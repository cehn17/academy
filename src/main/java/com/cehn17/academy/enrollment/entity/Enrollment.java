package com.cehn17.academy.enrollment.entity;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.student.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_schedule_id")
    private CourseSchedule courseSchedule;

    private LocalDateTime enrollmentDate = LocalDateTime.now();

    private String status = "ACTIVE";
}
