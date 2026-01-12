package com.cehn17.academy.teacherreview.dto;

import java.time.LocalDateTime;

public record TeacherReviewResponse(
        String id,          // ID de Mongo (String)
        Long studentId,     // ID de MySQL
        Long teacherId,     // ID de MySQL
        String courseName,  // Nombre del curso (Opcional, si queremos traerlo)
        Integer score,
        Integer year,
        Integer semester,
        String comment,
        LocalDateTime createdAt
) {}