package com.cehn17.academy.student.repository;

import com.cehn17.academy.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUserId(Long userId);
    Optional<Student> findByUserUsername(String username);

}
