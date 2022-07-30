package com.crud.springbootcrud.service.mapper;

import com.crud.springbootcrud.model.User;
import com.crud.springbootcrud.model.dto.UserDto;
import com.crud.springbootcrud.model.dto.UserRegistrationDto;
import com.crud.springbootcrud.service.RoleService;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = RoleService.class)
public interface UserMapper {


    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> users);

    User toUser(UserDto userDto);

    User toUser(UserRegistrationDto userRegistrationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromUserDto(UserDto userDto, @MappingTarget User user);

}
