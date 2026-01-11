package com.cehn17.academy.courseschedule.service;

import com.cehn17.academy.courseschedule.dto.CourseScheduleRequest;
import com.cehn17.academy.courseschedule.dto.CourseScheduleResponse;

import java.util.List;

public interface CourseScheduleService {
    CourseScheduleResponse create(CourseScheduleRequest request);
    List<CourseScheduleResponse> getAll();
    List<CourseScheduleResponse> getByCourseId(Long courseId);
    void delete(Long id);
}
