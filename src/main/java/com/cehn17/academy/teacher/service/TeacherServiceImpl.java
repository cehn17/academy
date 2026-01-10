package com.cehn17.academy.teacher.service;

import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.mapper.TeacherMapper;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<TeacherResponseDTO> findAll() {
        return teacherRepository.findAll()
                .stream().map(teacherMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
