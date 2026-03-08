package com.cehn17.academy.courseschedule.controller;

import com.cehn17.academy.courseschedule.dto.CourseScheduleRequest;
import com.cehn17.academy.courseschedule.dto.CourseScheduleResponse;
import com.cehn17.academy.courseschedule.service.CourseScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Course Schedule Controller", description = "Operations for managing academic course timetables")
public class CourseScheduleController {

    private final CourseScheduleService scheduleService;

    @Operation(summary = "Create a new schedule", description = "Defines a new time slot for a specific course. Requires CREATE_SCHEDULE authority.")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SCHEDULE')")
    public ResponseEntity<CourseScheduleResponse> create(@Valid @RequestBody CourseScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    @Operation(summary = "Get all schedules", description = "Retrieves a list of all course schedules registered in the system. Requires READ_ALL_SCHEDULES authority.")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_SCHEDULES')")
    public ResponseEntity<List<CourseScheduleResponse>> getAll() {
        return ResponseEntity.ok(scheduleService.getAll());
    }

    @Operation(summary = "Get schedules by course ID", description = "Retrieves all time slots associated with a specific course identifier. Useful for student and teacher calendars.")
    // Endpoint útil: Buscar horarios por curso (ej: /schedules/course/5)
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAuthority('READ_ALL_SCHEDULES')")
    public ResponseEntity<List<CourseScheduleResponse>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(scheduleService.getByCourseId(courseId));
    }

    @Operation(summary = "Delete schedule", description = "Permanently removes a specific schedule entry by its ID. Requires DELETE_SCHEDULE authority.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SCHEDULE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
