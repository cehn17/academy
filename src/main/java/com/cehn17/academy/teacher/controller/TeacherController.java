package com.cehn17.academy.teacher.controller;

import com.cehn17.academy.common.PaginatedResponse;
import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.dto.TeacherUpdateRequest;
import com.cehn17.academy.teacher.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Controller", description = "Operations for teacher profile management and administrative teacher control")
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(summary = "Get my profile", description = "Retrieves the personal profile information of the currently authenticated teacher.")
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    public ResponseEntity<TeacherResponseDTO> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(teacherService.getMyProfile(username));
    }

    @Operation(summary = "Update my profile", description = "Allows the authenticated teacher to update their own profile details.")
    @PutMapping("/me")
    @PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
    public ResponseEntity<TeacherResponseDTO> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody TeacherUpdateRequest request) {

        String username = authentication.getName();
        return ResponseEntity.ok(teacherService.updateMyProfile(username, request));
    }

    @Operation(summary = "Get all teachers", description = "Retrieves a paginated list of all teachers registered in the system. Restricted to staff with READ_ALL_TEACHERS authority.")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_TEACHERS')")
    public ResponseEntity<PaginatedResponse<TeacherResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "lastName") Pageable pageable
    ) {
        Page<TeacherResponseDTO> page = teacherService.findAll(pageable);
        return ResponseEntity.ok(new PaginatedResponse<>(page));
    }

    @Operation(summary = "Get teacher by ID", description = "Retrieves detailed information of a specific teacher using their unique ID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @Operation(summary = "Delete teacher", description = "Permanently removes a teacher record from the system. Requires DELETE_TEACHER authority.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TEACHER')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update teacher by Admin", description = "Allows an administrator to modify any teacher's information by their ID.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ANY_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> updateTeacherByAdmin(
            @PathVariable Long id,
            @Valid @RequestBody TeacherUpdateRequest request) {

        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }

}
