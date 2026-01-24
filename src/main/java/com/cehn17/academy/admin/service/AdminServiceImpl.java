package com.cehn17.academy.admin.service;

import com.cehn17.academy.admin.dto.AdminStatsDTO;
import com.cehn17.academy.course.repository.CourseRepository;
import com.cehn17.academy.enrollment.repository.EnrollmentRepository;
import com.cehn17.academy.teacherreview.repository.TeacherReviewRepository;
import com.cehn17.academy.user.repository.UserRepository;
import com.cehn17.academy.user.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final TeacherReviewRepository reviewRepository;

    @Override
    public AdminStatsDTO getDashboardStats() {
        // 1. Obtener métricas de MySQL
        long studentsCount = userRepository.countByRole(Role.STUDENT);
        long teachersCount = userRepository.countByRole(Role.TEACHER);
        long coursesCount = courseRepository.count();

        // 2. Obtener métricas de MongoDB
        long reviewsCount = reviewRepository.count();

        // 3. (Opcional)
        // buscar las últimas 5 inscripciones y mapearlas a String
        List<String> recentActivity = List.of(
                "Nuevo alumno inscrito en Java",
                "Nuevo curso creado: Spring Boot Avanzado"
        );
        // TODO sugerencia para hacerlo real: enrollmentRepository.findTop5ByOrderByCreatedAtDesc()

        // 4. Retornar el Record lleno
        return new AdminStatsDTO(
                studentsCount,
                teachersCount,
                coursesCount,
                reviewsCount,
                0.0, // TODO: pendiente de calcular
                recentActivity
        );
    }
}
