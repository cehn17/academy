package com.cehn17.academy.common;

import org.springframework.data.domain.Page;
import java.util.List;

// 1. Defines los campos en el paréntesis (Componentes)
public record PaginatedResponse<T>(
        List<T> content,
        int pageNumber,
        int totalPages,
        long totalElements
) {
    // 2. Constructor personalizado para facilitar la conversión desde Page
    public PaginatedResponse(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
