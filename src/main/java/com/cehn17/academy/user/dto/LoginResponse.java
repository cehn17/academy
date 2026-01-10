package com.cehn17.academy.user.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        String jwt,
        String username,
        String role
) {}
