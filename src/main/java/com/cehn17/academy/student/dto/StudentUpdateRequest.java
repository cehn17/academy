package com.cehn17.academy.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentUpdateRequest(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        // El DNI suele ser fijo, mejor no dejarlo editar salvo por un admin.
        // String dni,

        @Size(max = 255, message = "La dirección es muy larga")
        String address
) { }
