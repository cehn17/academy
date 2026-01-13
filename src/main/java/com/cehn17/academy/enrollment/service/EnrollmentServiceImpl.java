package com.cehn17.academy.enrollment.service;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.courseschedule.repository.CourseScheduleRepository;
import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.dto.GradeUpdateRequest;
import com.cehn17.academy.enrollment.dto.GradeUpdateResponseDTO;
import com.cehn17.academy.enrollment.entity.Enrollment;
import com.cehn17.academy.enrollment.mapper.EnrollmentMapper;
import com.cehn17.academy.enrollment.repository.EnrollmentRepository;
import com.cehn17.academy.exception.ResourceNotFoundException;
import com.cehn17.academy.student.entity.Student;
import com.cehn17.academy.student.repository.StudentRepository;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import com.cehn17.academy.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseScheduleRepository courseScheduleRepository;
    private final TeacherRepository teacherRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    @Transactional
    public EnrollmentResponseDTO enrollStudent(String username, EnrollmentRequest request) {
        // 1. Buscar al Alumno por su usuario (del Token)
        Optional<Student> student = studentRepository.findByUserUsername(username);
        if(student.isEmpty()){
            return new EnrollmentResponseDTO(
                    null, null, null, null,null,null,null, // Datos vacíos
                    false,                  // success
                    "Alumno no encontrado"            // mensaje
            );
        }
        // 2. Buscar el Horario al que se quiere anotar
        Optional<CourseSchedule> newSchedule = courseScheduleRepository.findById(request.courseScheduleId());
        if(newSchedule.isEmpty()){
            return new EnrollmentResponseDTO(
                    null, null, null, null,null,null, null, // Datos vacíos
                    false,                  // success
                    "Horario no encontrado"            // mensaje
            );
        }

        //  VALIDACIÓN: ¿Ya está inscrito?
        if (enrollmentRepository.existsByStudentAndCourseSchedule(student.get(), newSchedule.get())) {
            return new EnrollmentResponseDTO(
                    null, null, null, null,null,null, null, // Datos vacíos
                    false,                  // success
                    "El estudiante ya está inscrito en este curso y horario."            // mensaje
            );
        }

        // 2. VALIDACIÓN DE SOLAPAMIENTO (La lógica nueva)
        List<Enrollment> existingEnrollments = enrollmentRepository.findByStudent(student.get());

        for (Enrollment enrollment : existingEnrollments) {
            CourseSchedule existingSchedule = enrollment.getCourseSchedule();

            if (hasTimeConflict(newSchedule.get(), existingSchedule)) {
                // 🛑 DETECTAMOS CONFLICTO: Retornamos DTO de error y SALIMOS
                String conflictoMsg = String.format(
                        "No se pudo inscribir. Conflicto de horarios con el curso '%s' (%s %s - %s)",
                        existingSchedule.getCourse().getName(),
                        existingSchedule.getDays(),
                        existingSchedule.getStartTime(),
                        existingSchedule.getEndTime()
                );

                // Retornamos objeto fallido (id null, success false)
                return new EnrollmentResponseDTO(
                        null, null, null, null, null, null, null,// Datos vacíos
                        false,                  // success
                        conflictoMsg            // mensaje
                );
            }
        }

        // 4. Crear la entidad
        Enrollment enrollment = enrollmentMapper.toEntity(request);
        enrollment.setStudent(student.get());
        enrollment.setCourseSchedule(newSchedule.get());

        // 5. Guardar y Retornar
        Enrollment saved = enrollmentRepository.save(enrollment);

        // 5. Retornamos objeto exitoso
        return enrollmentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getMyEnrollments(String username) {
        return enrollmentRepository.findByStudentUserUsername(username)
                .stream()
                .map(enrollmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GradeUpdateResponseDTO updateGrade(Long enrollmentId, GradeUpdateRequest request) {
        // 1. Buscar la inscripción
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));

        // 2. SEGURIDAD: Validar quién está intentando poner la nota
        validarPermisoDeProfesor(enrollment);

        // 3. Actualizar nota
        enrollment.setFinalGrade(request.grade());

        // 4. Guardar
        Enrollment saved = enrollmentRepository.save(enrollment);

        // 5. Retornar DTO (Asumiendo que tienes un mapper)
        return enrollmentMapper.toGradeResponseDTO(saved);
    }

    private void validarPermisoDeProfesor(Enrollment enrollment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Si es ADMIN, pase directo (el "Director" puede cambiar cualquier nota)
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));
        if (isAdmin) return;

        // Si es TEACHER, verificamos que sea EL profesor de ESTE curso
        Teacher teacher = teacherRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ASUMCIÓN: Tu entidad 'Course' tiene un campo 'teacher' o 'User teacher'
        // Ajusta esta línea según tu modelo real:
        boolean profesorDelCurso = enrollment.getCourseSchedule().getTeachers().contains(teacher);

        if (!profesorDelCurso) {
            // Importante: Usamos AccessDeniedException para devolver un 403
            throw new org.springframework.security.access.AccessDeniedException(
                    "No tienes permiso para calificar a este alumno. No es tu curso."
            );
        }
    }

    private boolean hasTimeConflict(CourseSchedule newSchedule, CourseSchedule existingSchedule) {
        // 1. Verificar si comparten algún día
        // Collections.disjoint devuelve true si NO tienen elementos en común.
        // Nosotros queremos saber si SI tienen en común, por eso lo negamos (!).
        boolean sharedDays = !Collections.disjoint(newSchedule.getDays(), existingSchedule.getDays());

        // Si no comparten días, no hay conflicto.
        if (!sharedDays) {
            return false;
        }

        // 2. Si comparten días, verificamos si se solapan las horas
        // Fórmula de solapamiento de intervalos:
        // (NuevoInicio < ViejoFin) AND (NuevoFin > ViejoInicio)
        boolean overlap = newSchedule.getStartTime().isBefore(existingSchedule.getEndTime()) &&
                newSchedule.getEndTime().isAfter(existingSchedule.getStartTime());

        return overlap;
    }
}
