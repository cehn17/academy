package com.cehn17.academy.course.dto;

import jakarta.validation.constraints.Size;

import java.util.Set;

public record CourseUpdateRequest(
        @Size(min = 3, message = "El nombre es muy corto")
        String name,
        String description,
        Set<Long> teacherIds
) {
}
