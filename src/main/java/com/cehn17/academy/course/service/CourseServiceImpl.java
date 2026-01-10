package com.cehn17.academy.course.service;

import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;
import com.cehn17.academy.course.dto.CourseUpdateRequest;
import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.course.mapper.CourseMapper;
import com.cehn17.academy.course.repository.CourseRepository;
import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseCreateRequest request) {

        // 1. Validar nombre único
        if (courseRepository.existsByName(request.name())) {
            throw new RuntimeException("El nombre del curso ya existe");
        }

        Set<Teacher> teachersSet = new HashSet<>();

        // 2. Manejo seguro de Profesores (Null Safe)
        if (request.teacherIds() != null && !request.teacherIds().isEmpty()) {

            // Truco Pro: Eliminamos duplicados del Request por si mandan [1, 1]
            Set<Long> uniqueIds = new HashSet<>(request.teacherIds());

            List<Teacher> teachersFound = teacherRepository.findAllById(uniqueIds);

            // Validación estricta: ¿Están todos los que son?
            if (teachersFound.size() != uniqueIds.size()) {
                throw new ObjectNotFoundException("Algunos IDs de profesores no son válidos");
            }

            teachersSet.addAll(teachersFound);
        }

        // 3. Crear entidad y asignar
        Course course = courseMapper.toEntity(request);
        course.setTeachers(teachersSet);

        // 4. Guardar y Retornar
        Course courseSaved = courseRepository.save(course);
        return courseMapper.toResponseDTO(courseSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Curso no encontrado con ID: " + id));
        return courseMapper.toResponseDTO(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        Page<Course> coursesPage = courseRepository.findAll(pageable);
        return coursesPage.map(courseMapper::toResponseDTO);
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseUpdateRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Curso no encontrado con ID: " + id));

        //Validar si el nuevo nombre ya existe (solo si cambió el nombre)
        if (request.name() != null &&
                !request.name().equals(course.getName()) &&
                courseRepository.existsByName(request.name())) {
            throw new RuntimeException("Ya existe otro curso con ese nombre");
        }

        courseMapper.updateCourseFromDto(request, course);

        if (request.teacherIds() != null) {
            Set<Long> uniqueIds = new HashSet<>(request.teacherIds());
            List<Teacher> teachersFound = teacherRepository.findAllById(uniqueIds);

            if (teachersFound.size() != uniqueIds.size()) {
                throw new ObjectNotFoundException("Algunos IDs de profesores no son válidos");
            }

            course.setTeachers(new HashSet<>(teachersFound));
        }

        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponseDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ObjectNotFoundException("No se puede eliminar. Curso no encontrado con ID: " + id);
        }

        courseRepository.deleteById(id);
    }
}
