package com.cehn17.academy.enrollment.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull(message = "El ID del horario (Course Schedule) es obligatorio")
        Long courseScheduleId
) {}
