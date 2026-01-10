package com.cehn17.academy.enrollment.repository;

import com.cehn17.academy.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentUserUsername(String username);
}
