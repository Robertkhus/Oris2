package org.example.orishw2.mapper;

import org.example.orishw2.dto.RegistrationForm;
import org.example.orishw2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "password", source = "password")
    User mapToUser(RegistrationForm form, String password);

}
