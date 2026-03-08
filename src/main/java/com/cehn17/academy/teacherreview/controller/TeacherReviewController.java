package com.cehn17.academy.teacherreview.controller;

import com.cehn17.academy.teacherreview.dto.TeacherReviewRequest;
import com.cehn17.academy.teacherreview.dto.TeacherReviewResponse;
import com.cehn17.academy.teacherreview.entity.TeacherReview;
import com.cehn17.academy.teacherreview.service.TeacherReviewServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Teacher Review Controller", description = "Management of student feedback and teacher evaluations stored in MongoDB")
public class TeacherReviewController {

    private final TeacherReviewServiceImpl teacherReviewServiceImpl;

    @Operation(summary = "Get all reviews", description = "Retrieves a comprehensive list of all teacher reviews. Access is typically restricted to ADMINISTRATOR profiles.")
    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_REVIEWS')")
    public ResponseEntity<List<TeacherReviewResponse>> findAll(){
        return ResponseEntity.ok(teacherReviewServiceImpl.findAll());
    }

    @Operation(summary = "Submit a new review", description = "Allows an authenticated student to rate and comment on a teacher. Business Rule: A student can only review a teacher once per course and academic semester.")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_REVIEW')")
    public ResponseEntity<TeacherReviewResponse> create(@Valid @RequestBody TeacherReviewRequest request,
                                                        Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacherReviewServiceImpl.createReview(authentication.getName(), request));
    }

    @Operation(summary = "Get review by ID", description = "Retrieves the full details of a specific evaluation using its unique MongoDB ID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_REVIEW')")
    public ResponseEntity<TeacherReviewResponse> getReviewById(@PathVariable String id) {
        return ResponseEntity.ok(teacherReviewServiceImpl.getReviewById(id));
    }

    @Operation(summary = "Get my reviews", description = "Retrieves a list of reviews associated with the currently authenticated user (either reviews written by a student or received by a teacher).")
    @GetMapping("/my-reviews")
    @PreAuthorize("hasAuthority('READ_MY_REVIEWS')")
    public ResponseEntity<List<TeacherReviewResponse>> getMyReviews(Authentication authentication) {
        return ResponseEntity.ok(teacherReviewServiceImpl.getMyReviews(authentication.getName()));
    }

}
