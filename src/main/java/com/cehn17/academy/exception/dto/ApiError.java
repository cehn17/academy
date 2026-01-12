package com.cehn17.academy.exception.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ApiError(
        String backendMessage,
        String message,
        String url,
        String method,
        LocalDateTime timestamp
) implements Serializable {

    // Constructor de ayuda (sin timestamp, lo pone automático)
    public ApiError(String backendMessage, String message, String url, String method) {
        this(backendMessage, message, url, method, LocalDateTime.now());
    }
}
