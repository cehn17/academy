package com.cehn17.academy.course.dto;

import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import java.util.Set;

public record CourseResponseDTO(
        Long id,
        String name,
        String description,
        boolean active,
        Set<TeacherResponseDTO> teachers
) {}
