package com.cehn17.academy.courseschedule.service;

import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.course.repository.CourseRepository;
import com.cehn17.academy.courseschedule.dto.CourseScheduleRequest;
import com.cehn17.academy.courseschedule.dto.CourseScheduleResponse;
import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.courseschedule.mapper.CourseScheduleMapper;
import com.cehn17.academy.courseschedule.repository.CourseScheduleRepository;
import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseScheduleServiceImpl implements CourseScheduleService {

    private final CourseScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseScheduleMapper scheduleMapper;

    @Transactional
    @Override
    public CourseScheduleResponse create(CourseScheduleRequest request) {
        // 1. Validar coherencia de horas
        if (request.startTime().isAfter(request.endTime())) {
            throw new RuntimeException("La hora de inicio no puede ser posterior a la de fin");
        }

        // 2. Buscar el Curso (Padre)
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ObjectNotFoundException("Curso no encontrado"));

        // 3. Buscar Profesores
        List<Teacher> teachers = new ArrayList<>();
        if (request.teacherIds() != null && !request.teacherIds().isEmpty()) {
            teachers = teacherRepository.findAllById(request.teacherIds());
            if (teachers.size() != request.teacherIds().size()) {
                throw new ObjectNotFoundException("Algunos profesores no existen");
            }
        }

        // 4. Mapear y Asignar
        CourseSchedule schedule = scheduleMapper.toEntity(request);
        schedule.setCourse(course);     // Asignamos relación ManyToOne
        schedule.setTeachers(teachers); // Asignamos relación ManyToMany

        // 5. Guardar
        return scheduleMapper.toResponseDTO(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public List<CourseScheduleResponse> getAll() {
        return scheduleMapper.toResponseList(scheduleRepository.findAll());
    }


    @Transactional(readOnly = true)
    public List<CourseScheduleResponse> getByCourseId(Long courseId) {
        return scheduleMapper.toResponseList(scheduleRepository.findByCourseId(courseId));
    }

    @Transactional
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ObjectNotFoundException("Horario no encontrado");
        }
        scheduleRepository.deleteById(id);
    }
}
