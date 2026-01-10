package com.cehn17.academy.courseschedule.repository;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
}
