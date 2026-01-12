package com.cehn17.academy.teacherreview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeacherReviewRequest(
        @NotNull(message = "Debes indicar el ID del profesor")
        Long teacherId,

        @NotNull(message = "Debes indicar el ID del curso")
        Long courseId,

        @Min(1) @Max(5)
        Integer score,

        @NotBlank(message = "El comentario no puede estar vacío")
        String comment,


        Integer year,

        @Min(1) @Max(2)
        Integer semester
) {}