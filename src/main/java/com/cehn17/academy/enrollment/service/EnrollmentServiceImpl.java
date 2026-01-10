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

import java.util.List;
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
        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Alumno no encontrado o no registrado correctamente"));

        // 2. Buscar el Horario al que se quiere anotar
        CourseSchedule schedule = courseScheduleRepository.findById(request.courseScheduleId())
                .orElseThrow(() -> new ObjectNotFoundException("El horario solicitado no existe"));

        // 3. (Validación Extra) Verificar si ya está inscripto en este horario
        // Podrías agregar un método 'existsByStudentAndCourseSchedule' en el repo si quisieras ser estricto.

        // 4. Crear la entidad
        Enrollment enrollment = enrollmentMapper.toEntity(request);
        enrollment.setStudent(student);
        enrollment.setCourseSchedule(schedule);

        // 5. Guardar y Retornar
        Enrollment saved = enrollmentRepository.save(enrollment);
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
}
