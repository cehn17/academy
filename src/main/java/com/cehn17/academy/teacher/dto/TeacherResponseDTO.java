package com.cehn17.academy.teacher.dto;

import java.util.Set;

public record TeacherResponseDTO(
        Long id,
        String name,
        String lastName,
        String specialty,
        String username,
        String email,
        Set<String> courses
) {}
