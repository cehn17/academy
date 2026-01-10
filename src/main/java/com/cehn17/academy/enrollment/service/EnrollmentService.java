package com.cehn17.academy.enrollment.service;

import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;

import java.util.List;

public interface EnrollmentService {
    public EnrollmentResponseDTO enrollStudent(String username, EnrollmentRequest request);
    public List<EnrollmentResponseDTO> getMyEnrollments(String username);
}
