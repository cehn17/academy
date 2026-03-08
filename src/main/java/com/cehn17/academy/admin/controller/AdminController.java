package com.cehn17.academy.admin.controller;

import com.cehn17.academy.admin.dto.AdminStatsDTO;
import com.cehn17.academy.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Panel de Administrador", description = "Métricas y reportes globales")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Obtener estadísticas", description = "Devuelve contadores de usuarios, cursos y reviews.")
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ADMIN_PANEL')")
    public ResponseEntity<AdminStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }
}
