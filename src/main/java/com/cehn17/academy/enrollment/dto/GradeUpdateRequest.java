package com.cehn17.academy.enrollment.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record GradeUpdateRequest(
        @NotNull(message = "La nota es obligatoria")
        @DecimalMin(value = "0.0", message = "La nota mínima es 0")
        @DecimalMax(value = "10.0", message = "La nota máxima es 10")
        Double grade
) {}