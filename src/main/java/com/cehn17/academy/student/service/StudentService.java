package com.cehn17.academy.student.service;

import com.cehn17.academy.student.dto.StudentResponseDTO;
import com.cehn17.academy.student.dto.StudentUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    public StudentResponseDTO getMyProfile(String username);

    StudentResponseDTO updateMyProfile(String username, @Valid StudentUpdateRequest request);

    Page<StudentResponseDTO> getAllStudents(Pageable pageable);

    StudentResponseDTO getStudentById(Long id);

    void deleteStudent(Long id);

    StudentResponseDTO updateStudent(Long id, @Valid StudentUpdateRequest request);
}
