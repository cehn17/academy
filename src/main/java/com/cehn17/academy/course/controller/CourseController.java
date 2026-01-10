package com.cehn17.academy.course.controller;

import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;
import com.cehn17.academy.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    // Solo el ADMIN o un TEACHER debería poder crear cursos
    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    public ResponseEntity<CourseResponseDTO> create(@Valid @RequestBody CourseCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_COURSES')")
    public ResponseEntity<List<CourseResponseDTO>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_COURSE')")
    public ResponseEntity<CourseResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
}
