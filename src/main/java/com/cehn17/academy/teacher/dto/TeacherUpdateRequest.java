package com.cehn17.academy.teacher.dto;

import jakarta.validation.constraints.NotBlank;

public record TeacherUpdateRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        String specialty
) {
}
