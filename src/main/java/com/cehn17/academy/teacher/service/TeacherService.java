package com.cehn17.academy.teacher.service;

import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.dto.TeacherUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {

    TeacherResponseDTO getMyProfile(String username);

    Page<TeacherResponseDTO> findAll(Pageable pageable);

    TeacherResponseDTO updateMyProfile(String username, TeacherUpdateRequest request);

    TeacherResponseDTO getTeacherById(Long id);

    void deleteTeacher(Long id);

    TeacherResponseDTO updateTeacher(Long id, TeacherUpdateRequest request);
}
