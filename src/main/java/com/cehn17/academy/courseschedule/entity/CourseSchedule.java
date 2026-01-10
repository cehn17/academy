package com.cehn17.academy.courseschedule.entity;

import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.courseschedule.util.DayOfWeek;
import com.cehn17.academy.enrollment.entity.Enrollment;
import com.cehn17.academy.teacher.entity.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_schedules")
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany
    @JoinTable(
            name = "course_schedule_teachers",
            joinColumns = @JoinColumn(name = "course_schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "schedule_days", joinColumns = @JoinColumn(name = "schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_day")
    private Set<DayOfWeek> days;


    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    @OneToMany(mappedBy = "courseSchedule")
    private List<Enrollment> enrollments;
}