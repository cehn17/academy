package com.cehn17.academy.enrollment.controller;

import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.dto.GradeUpdateRequest;
import com.cehn17.academy.enrollment.dto.GradeUpdateResponseDTO;
import com.cehn17.academy.enrollment.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Enrollment Controller", description = "Operations for student course registration and grade management")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // 1. INSCRIBIRSE (POST)
    @Operation(summary = "Enroll in a course", description = "Allows the authenticated student to register for a specific course. Requires CREATE_ENROLLMENT authority.")
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
    @Operation(summary = "Get my enrollments", description = "Retrieves a list of all courses the authenticated student is currently enrolled in.")
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('READ_MY_ENROLLMENTS')")
    public ResponseEntity<List<EnrollmentResponseDTO>> getMyEnrollments(Authentication authentication) {
        return ResponseEntity.ok(enrollmentService.getMyEnrollments(authentication.getName()));
    }

    @Operation(summary = "Update enrollment grade", description = "Assigns or updates a student's grade for a specific enrollment. Restricted to ADMIN or TEACHER roles.")
    @PatchMapping("/{id}/grade")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<GradeUpdateResponseDTO> updateGrade(
            @PathVariable Long id,
            @Valid @RequestBody GradeUpdateRequest request) {

        return ResponseEntity.ok(enrollmentService.updateGrade(id, request));
    }


}
