package com.cehn17.academy.admin.dto;

import java.util.List;

public record AdminStatsDTO(
        long totalStudents,
        long totalTeachers,
        long totalCourses,
        long totalReviews,
        double revenue,
        List<String> recentActivity
) {}
