package com.cehn17.academy.teacherreview.service;

import com.cehn17.academy.teacherreview.dto.TeacherReviewRequest;
import com.cehn17.academy.teacherreview.dto.TeacherReviewResponse;

import java.util.List;

public interface TeacherReviewService {
    TeacherReviewResponse createReview(String username, TeacherReviewRequest request);
    List<TeacherReviewResponse> findAll();
}
