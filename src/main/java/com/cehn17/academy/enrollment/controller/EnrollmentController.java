package com.cehn17.academy.enrollment.controller;

import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // 1. INSCRIBIRSE (POST)
    @PostMapping
    // Asegurate de que el Rol STUDENT tenga este permiso en tu Enum
    @PreAuthorize("hasAuthority('CREATE_ENROLLMENT')")
    public ResponseEntity<EnrollmentResponseDTO> enroll(@Valid @RequestBody EnrollmentRequest request,
                                                        Authentication authentication) {

        EnrollmentResponseDTO response = enrollmentService.enrollStudent(authentication.getName(), request);

        if (response.success()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    // 2. VER MIS INSCRIPCIONES (GET)
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('READ_MY_ENROLLMENTS')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getMyEnrollments(Authentication authentication) {
        return ResponseEntity.ok(enrollmentService.getMyEnrollments(authentication.getName()));
    }
}
