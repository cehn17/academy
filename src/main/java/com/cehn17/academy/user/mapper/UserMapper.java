package com.cehn17.academy.user.mapper;

import com.cehn17.academy.user.dto.UserRequest;
import com.cehn17.academy.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Convierte el Request (DTO) a Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserRequest request, @MappingTarget User user);


    // @Mapping(target = "password", ignore = true) // Nunca devolver la contraseña
    // UserRequest toDTO(User user);
}
