package com.cehn17.academy.enrollment.service;

import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.dto.GradeUpdateRequest;
import com.cehn17.academy.enrollment.dto.GradeUpdateResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface EnrollmentService {
    public EnrollmentResponseDTO enrollStudent(String username, EnrollmentRequest request);
    public List<EnrollmentResponseDTO> getMyEnrollments(String username);

    GradeUpdateResponseDTO updateGrade(Long id, GradeUpdateRequest request);
}
