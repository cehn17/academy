package com.cehn17.academy.teacherreview.repository;

import com.cehn17.academy.teacherreview.entity.TeacherReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TeacherReviewRepository extends MongoRepository<TeacherReview, String> {

    List<TeacherReview> findByTeacherId(Long teacherId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<TeacherReview> findByStudentId(Long studentId);
}
