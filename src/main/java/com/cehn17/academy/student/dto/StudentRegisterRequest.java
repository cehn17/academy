package com.cehn17.academy.student.dto;

import com.cehn17.academy.user.dto.UserRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record StudentRegisterRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^\\d{7,8}$", message = "El DNI debe tener 7 u 8 dígitos")
        String dni,

        String address,

        @Valid //CRÍTICO: Esto le dice a Spring que también valide el objeto interno
        @NotNull(message = "Los datos de usuario son obligatorios")
        UserRequest user
) {}
