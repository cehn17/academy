package com.cehn17.academy.teacherreview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "teacher_reviews") // Nombre de la colección en Mongo
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(
        name = "idx_review_unique_for_semester",
        def = "{'studentId': 1, 'courseId': 1, 'year': 1, 'semester': 1}",
        unique = true
)
public class TeacherReview {

    @Id
    private String id; // En Mongo los IDs suelen ser Strings alfanuméricos autogenerados

    // --- REFERENCIAS A MYSQL (Solo guardamos los IDs) ---
    private Long studentId;
    private Long teacherId;
    private Long courseId;

    // --- DATOS DE LA ENCUESTA ---
    private Integer score; // 1 a 5
    private String comment;

    private Integer year;     // Ej: 2025
    private Integer semester;

    // Puedes agregar cosas flexibles aquí sin miedo
    private boolean recommend;

    private LocalDateTime createdAt = LocalDateTime.now();
}
