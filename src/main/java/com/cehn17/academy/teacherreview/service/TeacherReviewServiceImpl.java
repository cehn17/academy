package com.cehn17.academy.teacherreview.service;

import com.cehn17.academy.course.repository.CourseRepository;
import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.exception.ResourceNotFoundException;
import com.cehn17.academy.student.entity.Student;
import com.cehn17.academy.student.repository.StudentRepository;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import com.cehn17.academy.teacherreview.dto.TeacherReviewRequest;
import com.cehn17.academy.teacherreview.dto.TeacherReviewResponse;
import com.cehn17.academy.teacherreview.entity.TeacherReview;
import com.cehn17.academy.teacherreview.repository.TeacherReviewRepository;
import com.cehn17.academy.user.entity.User;
import com.cehn17.academy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherReviewServiceImpl implements TeacherReviewService {

    private final TeacherReviewRepository reviewRepository; // Mongo 🍃
    private final StudentRepository studentRepository;      // MySQL 🐬
    private final TeacherRepository teacherRepository;      // MySQL 🐬
    private final CourseRepository courseRepository;        // MySQL 🐬
    private final UserRepository userRepository;

    @Override
    public TeacherReviewResponse createReview(String username, TeacherReviewRequest request) {
        // 1. Validar que el Alumno existe (MySQL)
        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Alumno no encontrado"));

        // 2. Validar que el Profesor existe (MySQL)
        Teacher teacher = teacherRepository.findById(request.teacherId())
                .orElseThrow(() -> new ObjectNotFoundException("Profesor no encontrado"));

        // 3. Validar Curso (Opcional, pero recomendado) (MySQL)
        if (!courseRepository.existsById(request.courseId())) {
            throw new ObjectNotFoundException("Curso no encontrado");
        }

        // 4. Mapear y Guardar en MongoDB 🍃
        TeacherReview review = new TeacherReview();
        review.setStudentId(student.getId());
        review.setTeacherId(teacher.getId());
        review.setCourseId(request.courseId());
        review.setScore(request.score());
        review.setYear(request.year());
        review.setSemester(request.semester());
        review.setComment(request.comment());
        review.setCreatedAt(LocalDateTime.now());

        TeacherReview saved = reviewRepository.save(review);

        // 5. Retornar DTO
        return mapToResponse(saved);
    }

    @Override
    public List<TeacherReviewResponse> findAll() {
        return reviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Método auxiliar para mapear a DTO
    private TeacherReviewResponse mapToResponse(TeacherReview review) {
        return new TeacherReviewResponse(
                review.getId(),
                review.getStudentId(),
                review.getTeacherId(),
                "Curso ID: " + review.getCourseId(), // Podríamos buscar el nombre en MySQL si quisiéramos ser detallistas
                review.getScore(),
                review.getYear(),
                review.getSemester(),
                review.getComment(),
                review.getCreatedAt()
        );
    }

    public TeacherReviewResponse getReviewById(String id) {
        TeacherReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada con ID: " + id)); // Asegúrate de tener esta Exception o usa RuntimeException

        return mapToResponse(review);
    }

    public List<TeacherReviewResponse> getMyReviews(String username) {
        //String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("Student no encontrado"));

        List<TeacherReview> reviews = reviewRepository.findByStudentId(student.getId());

        return reviews.stream().map(this::mapToResponse).toList();
    }
}
