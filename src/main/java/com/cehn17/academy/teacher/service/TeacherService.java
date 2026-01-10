package com.cehn17.academy.teacher.service;

import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {

    TeacherResponseDTO getMyProfile(String username);

    Page<TeacherResponseDTO> findAll(Pageable pageable);
}
