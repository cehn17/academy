package com.cehn17.academy.user.repository;

import com.cehn17.academy.user.entity.User;
import com.cehn17.academy.user.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // Spring Data JPA traduce esto a: SELECT COUNT(u) FROM User u WHERE u.role = ?1
    long countByRole(Role role);
}
