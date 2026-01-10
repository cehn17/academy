package com.cehn17.academy.teacher.controller;

import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_TEACHERS')")
    public ResponseEntity<List<TeacherResponseDTO>> findAll() {
        return ResponseEntity.ok(teacherService.findAll());
    }
}
