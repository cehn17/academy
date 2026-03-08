package com.cehn17.academy.course.controller;

import com.cehn17.academy.common.PaginatedResponse;
import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;
import com.cehn17.academy.course.dto.CourseUpdateRequest;
import com.cehn17.academy.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Course Controller", description = "Operations related to academic course management")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Create a new course", description = "Registers a new course in the system. Requires CREATE_COURSE authority (ADMIN or TEACHER roles).")
    @PostMapping
    // Solo el ADMIN o un TEACHER debería poder crear cursos
    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    public ResponseEntity<CourseResponseDTO> create(@Valid @RequestBody CourseCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @Operation(summary = "Get all courses", description = "Retrieves a paginated list of all available courses. Requires READ_ALL_COURSES authority.")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_COURSES')")
    public ResponseEntity<PaginatedResponse<CourseResponseDTO>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable
    ) {
        Page<CourseResponseDTO> page = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(new PaginatedResponse<>(page));
    }

    @Operation(summary = "Get course by ID", description = "Retrieves detailed information of a specific course using its unique identifier.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_COURSE')")
    public ResponseEntity<CourseResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Operation(summary = "Update course", description = "Updates an existing course's information. Requires UPDATE_COURSE authority.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    public ResponseEntity<CourseResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CourseUpdateRequest request) {

        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @Operation(summary = "Delete course", description = "Permanently removes a course from the system. Typically restricted to ADMINISTRATOR profiles.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_COURSE')") // Generalmente solo para ADMIN
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build(); // Retorna 204 (Éxito sin contenido)
    }
}
