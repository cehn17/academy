package com.cehn17.academy.courseschedule.repository;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    List<CourseSchedule> findByCourseId(Long courseId);
}
