package com.cehn17.academy.courseschedule.dto;

import com.cehn17.academy.courseschedule.util.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record CourseScheduleRequest(
        @NotNull(message = "Debes especificar el ID del curso")
        Long courseId,

        List<Long> teacherIds, // Puede haber varios profesores

        @NotNull(message = "Debes especificar los días")
        Set<DayOfWeek> days, // Ej: ["MONDAY", "WEDNESDAY"]

        @NotNull(message = "Hora de inicio obligatoria")
        LocalTime startTime, // Ej: "09:00"

        @NotNull(message = "Hora de fin obligatoria")
        LocalTime endTime,   // Ej: "11:00"

        String room
) {}
