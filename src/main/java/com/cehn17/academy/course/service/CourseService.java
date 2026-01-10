package com.cehn17.academy.course.service;

import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;
import com.cehn17.academy.course.dto.CourseUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    CourseResponseDTO createCourse(CourseCreateRequest request);
    CourseResponseDTO getCourseById(Long id);
    Page<CourseResponseDTO> getAllCourses(Pageable pageable);
    CourseResponseDTO updateCourse(Long id, CourseUpdateRequest request);
    void deleteCourse(Long id);
}
