package com.cehn17.academy.config.security;


import com.cehn17.academy.user.entity.User;
import com.cehn17.academy.user.repository.UserRepository;
import com.cehn17.academy.user.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            // Verificamos si ya existe el admin para no duplicarlo ni pisarlo
            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@academy.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña segura
                admin.setRole(Role.ADMINISTRATOR); // Asumimos que tienes este ENUM

                userRepository.save(admin);

                System.out.println("✅ USUARIO ADMIN CREADO: admin / admin123");
            }
        };
    }
}
