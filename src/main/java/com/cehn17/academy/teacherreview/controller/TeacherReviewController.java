package com.cehn17.academy.teacherreview.controller;

import com.cehn17.academy.teacherreview.dto.TeacherReviewRequest;
import com.cehn17.academy.teacherreview.dto.TeacherReviewResponse;
import com.cehn17.academy.teacherreview.entity.TeacherReview;
import com.cehn17.academy.teacherreview.service.TeacherReviewServiceImpl;
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
public class TeacherReviewController {

    private final TeacherReviewServiceImpl teacherReviewServiceImpl;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_REVIEWS')")
    public ResponseEntity<List<TeacherReviewResponse>> findAll(){
        return ResponseEntity.ok(teacherReviewServiceImpl.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_REVIEW')")
    public ResponseEntity<TeacherReviewResponse> create(@Valid @RequestBody TeacherReviewRequest request,
                                                        Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacherReviewServiceImpl.createReview(authentication.getName(), request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_REVIEW')")
    public ResponseEntity<TeacherReviewResponse> getReviewById(@PathVariable String id) {
        return ResponseEntity.ok(teacherReviewServiceImpl.getReviewById(id));
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("hasAuthority('READ_MY_REVIEWS')")
    public ResponseEntity<List<TeacherReviewResponse>> getMyReviews(Authentication authentication) {
        return ResponseEntity.ok(teacherReviewServiceImpl.getMyReviews(authentication.getName()));
    }

}
