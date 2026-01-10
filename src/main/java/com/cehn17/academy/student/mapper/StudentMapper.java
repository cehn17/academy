package com.cehn17.academy.student.mapper;

import com.cehn17.academy.student.dto.StudentRegisterRequest;
import com.cehn17.academy.student.dto.StudentResponseDTO;
import com.cehn17.academy.student.dto.StudentUpdateRequest;
import com.cehn17.academy.student.entity.Student;
import com.cehn17.academy.user.mapper.UserMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StudentMapper {

    // 1. De Entidad a Respuesta (Lectura de perfil)
    // MapStruct usa la notación de punto para entrar en objetos anidados
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    // @Mapping(source = "user.role", target = "role")
    StudentResponseDTO toResponseDTO(Student student);

    // 2. De Request a Entidad (Registro)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    Student toEntity(StudentRegisterRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    void updateStudentFromDto(StudentUpdateRequest dto, @MappingTarget Student entity);
}
