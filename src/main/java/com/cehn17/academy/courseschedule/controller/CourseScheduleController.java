package com.cehn17.academy.courseschedule.controller;

import com.cehn17.academy.courseschedule.dto.CourseScheduleRequest;
import com.cehn17.academy.courseschedule.dto.CourseScheduleResponse;
import com.cehn17.academy.courseschedule.service.CourseScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class CourseScheduleController {

    private final CourseScheduleService scheduleService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SCHEDULE')")
    public ResponseEntity<CourseScheduleResponse> create(@Valid @RequestBody CourseScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_SCHEDULES')")
    public ResponseEntity<List<CourseScheduleResponse>> getAll() {
        return ResponseEntity.ok(scheduleService.getAll());
    }

    // Endpoint útil: Buscar horarios por curso (ej: /schedules/course/5)
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAuthority('READ_ALL_SCHEDULES')")
    public ResponseEntity<List<CourseScheduleResponse>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(scheduleService.getByCourseId(courseId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SCHEDULE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
