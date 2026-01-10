package com.cehn17.academy.user.controller;

import com.cehn17.academy.config.security.blacklist.TokenBlacklistServiceImpl;
import com.cehn17.academy.student.dto.StudentRegisterRequest;
import com.cehn17.academy.teacher.dto.TeacherRegisterRequest;
import com.cehn17.academy.user.dto.LoginRequest;
import com.cehn17.academy.user.dto.LoginResponse;
import com.cehn17.academy.user.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authService;

    private final TokenBlacklistServiceImpl tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register-student")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody StudentRegisterRequest request) {
        LoginResponse response = authService.registerStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register-teacher")
    public ResponseEntity<LoginResponse> registerTeacher(@Valid @RequestBody TeacherRegisterRequest request) {
        LoginResponse response = authService.registerTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<?, ?>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Blacklist logic
            tokenBlacklistService.blacklistToken(authHeader.substring(7));

            return ResponseEntity.ok(Map.of("message", "Logout exitoso."));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Token no encontrado"));
    }
}
