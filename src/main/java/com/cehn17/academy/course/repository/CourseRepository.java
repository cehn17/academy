package com.cehn17.academy.course.repository;

import com.cehn17.academy.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByActiveTrue();

    List<Course> findByTeachersId(Long teacherId);

    boolean existsByName(String name);
}