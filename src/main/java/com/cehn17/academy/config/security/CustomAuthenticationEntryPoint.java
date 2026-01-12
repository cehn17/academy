package com.cehn17.academy.config.security;

import com.cehn17.academy.exception.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // 1. Definimos el estado HTTP (401 Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 2. Creamos tu record ApiError
        // Nota: authException.getMessage() suele decir "Full authentication is required..." o "Bad credentials"
        ApiError apiError = new ApiError(
                authException.getMessage(),                  // backendMessage
                "Acceso denegado: Token inválido o no proporcionado.", // message amigable
                request.getRequestURI(),                     // url
                request.getMethod(),                         // method
                LocalDateTime.now()                          // timestamp
        );

        // 3. Escribimos el JSON manualmente en la respuesta
        objectMapper.writeValue(response.getOutputStream(), apiError);
    }
}
