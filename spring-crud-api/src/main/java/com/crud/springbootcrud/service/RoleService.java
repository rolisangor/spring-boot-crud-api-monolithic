package com.crud.springbootcrud.service;

import com.crud.springbootcrud.model.Role;
import com.crud.springbootcrud.model.dto.RoleDto;

import java.util.Collection;

public interface RoleService {

    Role getByName(String name);
    RoleDto save(Role role);
    boolean delete(String name);
    boolean existsByName(String name);
    Collection<RoleDto> getAll();
}
