package com.cehn17.academy.enrollment.repository;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.enrollment.entity.Enrollment;
import com.cehn17.academy.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentUserUsername(String username);

    // Buscar todas las inscripciones de un alumno específico
    List<Enrollment> findByStudent(Student student);

    // Validar si ya existe una inscripción de este alumno a este horario
    boolean existsByStudentAndCourseSchedule(Student student, CourseSchedule courseSchedule);
}
