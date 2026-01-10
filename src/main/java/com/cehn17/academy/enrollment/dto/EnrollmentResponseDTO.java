package com.cehn17.academy.enrollment.dto;

import java.time.LocalDateTime;

public record EnrollmentResponseDTO(
        Long id,
        String courseName,      // Ej: "Matemática I" (Sacado de courseSchedule.getCourse().getName())
        String scheduleInfo,    // Ej: "Lunes 08:00 - 12:00" (Sacado de courseSchedule)
        String studentUsername,
        LocalDateTime date,
        String status
) {}
