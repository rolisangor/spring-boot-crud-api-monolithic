package com.crud.springbootcrud.service.mapper;

import com.crud.springbootcrud.model.Role;
import com.crud.springbootcrud.model.dto.RoleDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);
    Collection<RoleDto> toRoleDtoCollection(Collection<Role> roleCollection);
}
