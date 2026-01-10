package com.cehn17.academy.course.mapper;

import com.cehn17.academy.course.dto.CourseCreateRequest;
import com.cehn17.academy.course.dto.CourseResponseDTO;
import com.cehn17.academy.course.dto.CourseUpdateRequest;
import com.cehn17.academy.course.entity.Course;
import com.cehn17.academy.teacher.mapper.TeacherMapper; // Importamos el otro mapper
import org.mapstruct.*;

// "uses = {TeacherMapper.class}" conecta los dos mappers
@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
public interface CourseMapper {

    CourseResponseDTO toResponseDTO(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true") // Por defecto nace activo

    // IMPORTANTE: Ignoramos los 'teachers' aquí.
    // MapStruct no sabe convertir una lista de Longs (IDs) a una lista de Objetos Teacher.
    // Eso es lógica de negocio (buscar en BD), así que lo haremos en el Service.
    @Mapping(target = "teachers", ignore = true)
    Course toEntity(CourseCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateCourseFromDto(CourseUpdateRequest request, @MappingTarget Course course);
}
