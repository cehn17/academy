package com.cehn17.academy.teacher.service;

import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.dto.TeacherUpdateRequest;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.mapper.TeacherMapper;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponseDTO getMyProfile(String username) {
        return teacherRepository.findByUserUsername(username) // Asumiendo que agregas este método al repo
                .map(teacherMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeacherResponseDTO> findAll(Pageable pageable) {

        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);
        return teacherPage.map(teacherMapper::toResponseDTO);
    }

    @Override
    public TeacherResponseDTO updateMyProfile(String username, TeacherUpdateRequest request) {
        Teacher teacher = teacherRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Teacher no encontrado para el usuario: " + username));
        teacherMapper.updateTeacherFromDto(request,teacher);
        Teacher teacherUpdate = teacherRepository.save(teacher);

        return teacherMapper.toResponseDTO(teacherUpdate);
    }

    @Override
    public TeacherResponseDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher no encontrado para el id: " + id));
        return teacherMapper.toResponseDTO(teacher);
    }



    @Override
    public TeacherResponseDTO updateTeacher(Long id, TeacherUpdateRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher no encontrado para el id: " + id));
        teacherMapper.updateTeacherFromDto(request,teacher);
        Teacher teacherUpdate = teacherRepository.save(teacher);
        return teacherMapper.toResponseDTO(teacherUpdate);
    }

    @Override
    @Transactional // Importante para que los cambios en las relaciones se guarden
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Profesor no encontrado con ID: " + id));

        for (Course course : teacher.getAssignedCourses()) {
            course.getTeachers().remove(teacher);
            // No hace falta llamar a save() explícitamente si estamos en una transacción,
            // Hibernate detecta que cambió la colección y actualiza la tabla intermedia.
        }

        for (CourseSchedule schedule : teacher.getAssignedSchedules()) {
            schedule.getTeachers().remove(teacher);
        }

        // Si esta CascadeType.ALL en la relación Teacher -> User, se borrará solo.
        // userRepository.delete(teacher.getUser());

        teacherRepository.delete(teacher);
    }
}
