package com.cehn17.academy.enrollment.service;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.courseschedule.repository.CourseScheduleRepository;
import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.entity.Enrollment;
import com.cehn17.academy.enrollment.mapper.EnrollmentMapper;
import com.cehn17.academy.enrollment.repository.EnrollmentRepository;
import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.student.entity.Student;
import com.cehn17.academy.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final EnrollmentMapper enrollmentMapper;

    @Override
    @Transactional
    public EnrollmentResponseDTO enrollStudent(String username, EnrollmentRequest request) {
        // 1. Buscar al Alumno por su usuario (del Token)
        Optional<Student> student = studentRepository.findByUserUsername(username);
        if(student.isEmpty()){
            return new EnrollmentResponseDTO(
                    null, null, null, null,null,null, // Datos vacíos
                    false,                  // success
                    "Alumno no encontrado"            // mensaje
            );
        }
        // 2. Buscar el Horario al que se quiere anotar
        Optional<CourseSchedule> newSchedule = courseScheduleRepository.findById(request.courseScheduleId());
        if(newSchedule.isEmpty()){
            return new EnrollmentResponseDTO(
                    null, null, null, null,null,null, // Datos vacíos
                    false,                  // success
                    "Horario no encontrado"            // mensaje
            );
        }

        //  VALIDACIÓN: ¿Ya está inscrito?
        if (enrollmentRepository.existsByStudentAndCourseSchedule(student.get(), newSchedule.get())) {
            return new EnrollmentResponseDTO(
                    null, null, null, null,null,null, // Datos vacíos
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
                        null, null, null, null, null, null,// Datos vacíos
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
