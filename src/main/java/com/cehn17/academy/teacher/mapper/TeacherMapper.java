package com.cehn17.academy.teacher.mapper;

import com.cehn17.academy.teacher.dto.TeacherRegisterRequest;
import com.cehn17.academy.teacher.dto.TeacherResponseDTO;
import com.cehn17.academy.teacher.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "specialization", target = "specialty")
    @Mapping(source = "assignedCourses", target = "courses")
    TeacherResponseDTO toResponseDTO(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "specialty", target = "specialization")
    @Mapping(target = "assignedSchedules", ignore = true)
    @Mapping(target = "assignedCourses", ignore = true)
    Teacher toEntity(TeacherRegisterRequest request);

    // MÉTODO AUXILIAR: MapStruct lo usará automáticamente para convertir
    // cada objeto Course de la lista en un simple String con su nombre.
    default String mapCourseToString(com.cehn17.academy.course.entity.Course course) {
        if (course == null) return null;
        return course.getName();
    }
}
