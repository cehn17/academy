package com.cehn17.academy.teacher.service;

import com.cehn17.academy.teacher.dto.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {

    TeacherResponseDTO getMyProfile(String username);

    List<TeacherResponseDTO> findAll();
}
