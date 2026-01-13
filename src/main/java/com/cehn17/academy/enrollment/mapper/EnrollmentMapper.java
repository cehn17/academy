package com.cehn17.academy.enrollment.mapper;

import com.cehn17.academy.courseschedule.entity.CourseSchedule;
import com.cehn17.academy.enrollment.dto.EnrollmentRequest;
import com.cehn17.academy.enrollment.dto.EnrollmentResponseDTO;
import com.cehn17.academy.enrollment.dto.GradeUpdateResponseDTO;
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

    // Tu método customizado (Perfecto)
    @Mapping(source = "courseSchedule", target = "scheduleInfo", qualifiedByName = "formatSchedule")

    // --- AGREGADO IMPORTANTE ---
    // Cuando convertimos una entidad existente a DTO, asumimos que es un registro válido/exitoso
    @Mapping(target = "success", constant = "true")
    @Mapping(target = "message", constant = "Inscripción realizada con éxito")
    EnrollmentResponseDTO toResponseDTO(Enrollment enrollment);


    // 2. DE REQUEST A ENTIDAD
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "courseSchedule", ignore = true)
    @Mapping(target = "enrollmentDate", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "finalGrade", ignore = true)
    Enrollment toEntity(EnrollmentRequest request);


    // 3. TU MÉTODO HELPER (Con un pequeño extra para español)
    @Named("formatSchedule")
    default String formatSchedule(CourseSchedule schedule) {
        if (schedule == null) return "Sin horario asignado";

        String daysString = "A confirmar";
        if (schedule.getDays() != null && !schedule.getDays().isEmpty()) {
            // Truco rápido: Traducción simple al vuelo si quieres español
            daysString = schedule.getDays().stream()
                    .map(day -> translateDay(day.name()))
                    .collect(Collectors.joining(", "));
        }

        return daysString + " " +
                schedule.getStartTime() + " - " +
                schedule.getEndTime() +
                " (Aula: " + (schedule.getRoom() != null ? schedule.getRoom() : "TBA") + ")";
    }

    // Pequeño helper para traducir (opcional, si lo quieres en español)
    default String translateDay(String dayInEnglish) {
        return switch (dayInEnglish) {
            case "MONDAY" -> "LUNES";
            case "TUESDAY" -> "MARTES";
            case "WEDNESDAY" -> "MIÉRCOLES";
            case "THURSDAY" -> "JUEVES";
            case "FRIDAY" -> "VIERNES";
            case "SATURDAY" -> "SÁBADO";
            case "SUNDAY" -> "DOMINGO";
            default -> dayInEnglish;
        };
    }


    // 1. DE ENTIDAD A DTO
    @Mapping(source = "enrollmentDate", target = "date")
    @Mapping(source = "student.user.username", target = "studentUsername")
    @Mapping(source = "courseSchedule.course.name", target = "courseName")
    @Mapping(source = "courseSchedule", target = "scheduleInfo", qualifiedByName = "formatSchedule")

    @Mapping(target = "success", constant = "true")
    @Mapping(target = "message", constant = "Nota cargada con éxito")
    GradeUpdateResponseDTO toGradeResponseDTO(Enrollment enrollment);
}
