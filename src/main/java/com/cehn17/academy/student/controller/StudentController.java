package com.cehn17.academy.student.controller;

import com.cehn17.academy.common.PaginatedResponse;
import com.cehn17.academy.student.dto.StudentResponseDTO;
import com.cehn17.academy.student.dto.StudentUpdateRequest;
import com.cehn17.academy.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    public ResponseEntity<StudentResponseDTO> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(studentService.getMyProfile(username));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('UPDATE_MY_PROFILE')")
    public ResponseEntity<StudentResponseDTO> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody StudentUpdateRequest request) {

        String username = authentication.getName();
        return ResponseEntity.ok(studentService.updateMyProfile(username, request));
    }

    // --- ÁREA ADMINISTRATIVA (Para Teachers y Admins) ---
    // El alumno NO tiene el permiso 'READ_ALL_STUDENTS' en su Enum, así que no podrá entrar aquí.

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_STUDENTS')")
    public ResponseEntity<PaginatedResponse<StudentResponseDTO>> getAllStudents(
            @PageableDefault(page = 0, size = 10, sort = "lastName") Pageable pageable
    ) {
        Page<StudentResponseDTO> page = studentService.getAllStudents(pageable);

        return ResponseEntity.ok(new PaginatedResponse<>(page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ONE_STUDENT')")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_STUDENT')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ANY_STUDENT')")
    public ResponseEntity<StudentResponseDTO> updateStudentByAdmin(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateRequest request) {

        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }
}
