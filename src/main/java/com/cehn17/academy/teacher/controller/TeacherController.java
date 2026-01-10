package com.cehn17.academy.teacher.controller;

import com.cehn17.academy.common.PaginatedResponse;
import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.dto.TeacherUpdateRequest;
import com.cehn17.academy.teacher.service.TeacherService;
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
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    public ResponseEntity<TeacherResponseDTO> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(teacherService.getMyProfile(username));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
    public ResponseEntity<TeacherResponseDTO> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody TeacherUpdateRequest request) {

        String username = authentication.getName();
        return ResponseEntity.ok(teacherService.updateMyProfile(username, request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_TEACHERS')")
    public ResponseEntity<PaginatedResponse<TeacherResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "lastName") Pageable pageable
    ) {
        Page<TeacherResponseDTO> page = teacherService.findAll(pageable);
        return ResponseEntity.ok(new PaginatedResponse<>(page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TEACHER')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ANY_TEACHER')")
    public ResponseEntity<TeacherResponseDTO> updateTeacherByAdmin(
            @PathVariable Long id,
            @Valid @RequestBody TeacherUpdateRequest request) {

        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }


}
