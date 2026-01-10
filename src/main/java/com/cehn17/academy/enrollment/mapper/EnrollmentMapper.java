package com.cehn17.academy.enrollment.mapper;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    // 1. DE ENTIDAD A DTO
    @Mapping(source = "enrollmentDate", target = "date")
    @Mapping(source = "student.user.username", target = "studentUsername")
    @Mapping(source = "courseSchedule.course.name", target = "courseName")

    // Aquí usamos el método customizado de abajo
    @Mapping(source = "courseSchedule", target = "scheduleInfo", qualifiedByName = "formatSchedule")
    EnrollmentResponseDTO toResponseDTO(Enrollment enrollment);


    // 2. DE REQUEST A ENTIDAD
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "courseSchedule", ignore = true)
    @Mapping(target = "enrollmentDate", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Enrollment toEntity(EnrollmentRequest request);



    @Named("formatSchedule")
    default String formatSchedule(CourseSchedule schedule) {
        if (schedule == null) return "Sin horario asignado";

        // Paso A: Convertir la lista de Enums (MONDAY, FRIDAY) a un String ("LUNES, VIERNES")
        // Nota: Por defecto saldrán en inglés (MONDAY). Si quieres español, podrías hacer un switch/map aquí.
        String daysString = "A confirmar";
        if (schedule.getDays() != null && !schedule.getDays().isEmpty()) {
            daysString = schedule.getDays().stream()
                    .map(Enum::name) // Obtiene "MONDAY", "TUESDAY"...
                    .collect(Collectors.joining(", "));
        }

        // Paso B: Construir la frase completa
        // Ej: "MONDAY, WEDNESDAY 18:00 - 22:00 (Aula 101)"
        return daysString + " " +
                schedule.getStartTime() + " - " +
                schedule.getEndTime() +
                " (Aula: " + schedule.getRoom() + ")";
    }
}
