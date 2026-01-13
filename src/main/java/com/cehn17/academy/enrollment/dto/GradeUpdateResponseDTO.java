package com.cehn17.academy.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GradeUpdateResponseDTO(
        Long id,
        String courseName,
        String scheduleInfo,
        String studentUsername,
        LocalDateTime date,
        String status,
        Double finalGrade,

        // Nuevos campos para manejar el resultado
        boolean success,
        String message
) {}
