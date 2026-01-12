package com.cehn17.academy.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

// JsonInclude.NON_NULL hace que si el ID es null (porque falló), no se envíe en el JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnrollmentResponseDTO(
        Long id,
        String courseName,      // Ej: "Matemática I" (Sacado de courseSchedule.getCourse().getName())
        String scheduleInfo,    // Ej: "Lunes 08:00 - 12:00" (Sacado de courseSchedule)
        String studentUsername,
        LocalDateTime date,
        String status,

        // Nuevos campos para manejar el resultado
        boolean success,       // true = inscrito, false = error
        String message         // "Inscripción exitosa" o "Conflicto con Matemáticas..."
) {}
