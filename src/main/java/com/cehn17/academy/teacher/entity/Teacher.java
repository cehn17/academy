package com.cehn17.academy.teacher.entity;

import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;

    private String specialization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Relación con los horarios asignados (la crearemos luego)
    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private List<CourseSchedule> assignedSchedules = new ArrayList<>();

    // mappedBy = "teachers" debe coincidir con el nombre de la variable en la clase Course
    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private Set<Course> assignedCourses = new HashSet<>();
}
