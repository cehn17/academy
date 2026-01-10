package com.cehn17.academy.course.entity;


import com.cehn17.academy.teacher.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Opcional: Útil si quieres construir cursos rápido en tests
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Builder.Default // Para que el builder respete el true por defecto
    private boolean active = true;

    // RELACIÓN MANY-TO-MANY
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_teachers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    // Importante: Inicializar el Set para evitar NullPointerException
    private Set<Teacher> teachers = new HashSet<>();

    // Opcional: Método helper para agregar profesores fácilmente
    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        // teacher.getCourses().add(this); // Si la relación fuera bidireccional
    }
}
