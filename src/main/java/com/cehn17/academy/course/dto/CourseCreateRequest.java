package com.cehn17.academy.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CourseCreateRequest(
        @NotBlank(message = "El nombre del curso es obligatorio")
        String name,

        String description,

        @NotEmpty(message = "El curso debe tener al menos un profesor asignado")
        Set<Long> teacherIds // Aquí recibimos [1, 5, 8]
) {}
