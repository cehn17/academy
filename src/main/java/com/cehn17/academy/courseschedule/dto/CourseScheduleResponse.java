package com.cehn17.academy.courseschedule.dto;

import com.cehn17.academy.courseschedule.util.DayOfWeek;
import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record CourseScheduleResponse(
        Long id,
        String courseName,
        List<TeacherResponseDTO> teachers,
        Set<DayOfWeek> days,
        LocalTime startTime,
        LocalTime endTime,
        String room
) {}
