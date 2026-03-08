package com.cehn17.academy.user.controller;

import com.cehn17.academy.config.security.blacklist.TokenBlacklistServiceImpl;
import com.cehn17.academy.student.dto.StudentRegisterRequest;
import com.cehn17.academy.teacher.dto.TeacherRegisterRequest;
import com.cehn17.academy.user.dto.LoginRequest;
import com.cehn17.academy.user.dto.LoginResponse;
import com.cehn17.academy.user.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller", description = "Endpoints for user authentication, registration, and session management using JWT")
public class AuthController {

    private final AuthenticationService authService;

    private final TokenBlacklistServiceImpl tokenBlacklistService;

    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token. This token must be used for all subsequent authorized requests.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(summary = "Register a new student", description = "Creates a new student account and automatically authenticates the user, returning a JWT token.")
    @PostMapping("/register-student")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody StudentRegisterRequest request) {
        LoginResponse response = authService.registerStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Register a new teacher", description = "Creates a new teacher account. Typically used by administrative staff to onboard educators.")
    @PostMapping("/register-teacher")
    public ResponseEntity<LoginResponse> registerTeacher(@Valid @RequestBody TeacherRegisterRequest request) {
        LoginResponse response = authService.registerTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Validate JWT token", description = "Checks if a provided JWT token is still valid and not expired or blacklisted.")
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @Operation(summary = "Logout user", description = "Invalidades the current JWT token by adding it to a blacklist. The token will no longer be usable for authentication.")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Blacklist logic
            tokenBlacklistService.blacklistToken(authHeader.substring(7));

            return ResponseEntity.ok(Map.of("message", "Logout exitoso."));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Token no encontrado"));
    }
}
