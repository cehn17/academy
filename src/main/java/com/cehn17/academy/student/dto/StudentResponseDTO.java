package com.cehn17.academy.student.dto;

public record StudentResponseDTO(
        Long id,
        String name,
        String lastName,
        String dni,
        String address,
        String username,
        String email
) {}
