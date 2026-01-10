package com.cehn17.academy.course.service;

import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;
import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.course.mapper.CourseMapper;
import com.cehn17.academy.course.repository.CourseRepository;
import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

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

        // 1. Buscar los profesores por sus IDs
        // findAllById es muy eficiente: SELECT * FROM teachers WHERE id IN (1, 5, 8...)
        List<Teacher> teachersFound = teacherRepository.findAllById(request.teacherIds());

        // 2. Validación: ¿Encontramos a todos los profes que pidieron?
        if (teachersFound.size() != request.teacherIds().size()) {
            // Podrías hilar fino y decir cuáles faltan, pero por ahora esto basta
            throw new ObjectNotFoundException("Algunos IDs de profesores no son válidos o no existen");
        }

        // 3. Crear la entidad base usando el Mapper (ignora los teachers por ahora)
        Course course = courseMapper.toEntity(request);

        // 4. Asignar manualmente los profesores encontrados
        // Convertimos la List a Set porque nuestra entidad usa Set
        course.setTeachers(new HashSet<>(teachersFound));

        // 5. Guardar en BD
        Course courseSaved = courseRepository.save(course);

        // 6. Devolver DTO
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
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
