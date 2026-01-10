package com.cehn17.academy.teacher.dto;

import com.cehn17.academy.user.dto.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record TeacherRegisterRequest(
        @NotBlank String name,
        @NotBlank String lastName,
        @NotBlank String dni,
        @NotBlank String specialty,
        @Valid UserRequest user
) {}
