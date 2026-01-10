package com.cehn17.academy.student.entity;

import com.cehn17.academy.enrollment.entity.Enrollment;
import com.cehn17.academy.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;

    @Column(unique = true)
    private String dni;

    private String address;

    // Relación con la cuenta de usuario
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Relación con las inscripciones (la crearemos luego)
    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
}
