package com.cehn17.academy.courseschedule.mapper;

import com.cehn17.academy.courseschedule.dto.CourseScheduleRequest;
import com.cehn17.academy.courseschedule.dto.CourseScheduleResponse;
import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.courseschedule.util.DayOfWeek;
import com.cehn17.academy.teacher.mapper.TeacherMapper;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
public interface CourseScheduleMapper {

    // REQUEST -> ENTITY
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    CourseSchedule toEntity(CourseScheduleRequest request);

    // ENTITY -> RESPONSE
    @Mapping(source = "course.name", target = "courseName")
    CourseScheduleResponse toResponseDTO(CourseSchedule schedule);

    List<CourseScheduleResponse> toResponseList(List<CourseSchedule> schedules);

    default Set<DayOfWeek> mapDays(Set<DayOfWeek> days) {
        if (days == null) {
            return null;
        }
        // Creamos un nuevo HashSet para asegurar que sea mutable y compatible con Hibernate
        return new HashSet<>(days);
    }
}
