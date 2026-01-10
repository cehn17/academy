package com.cehn17.academy.course.service;

import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;

import java.util.List;

public interface CourseService {
    CourseResponseDTO createCourse(CourseCreateRequest request);
    CourseResponseDTO getCourseById(Long id);
    List<CourseResponseDTO> getAllCourses();
}
