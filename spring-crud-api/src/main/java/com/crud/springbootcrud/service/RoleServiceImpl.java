package com.crud.springbootcrud.service;

import com.crud.springbootcrud.exception.BadRequestException;
import com.crud.springbootcrud.model.Role;
import com.crud.springbootcrud.model.dto.RoleDto;
import com.crud.springbootcrud.repository.RoleRepository;
import com.crud.springbootcrud.service.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() ->
                new BadRequestException("Role " + name + " not found"));
    }

    @Override
    public RoleDto save(Role role) {
        return roleMapper.toRoleDto(roleRepository.save(role));
    }

    @Override
    public boolean delete(String name) {
        if (!existsByName(name)) {
            throw new BadRequestException("Role " + name + " not found");
        }
        roleRepository.deleteByName(name);
        return true;
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public Collection<RoleDto> getAll() {
        return roleMapper.toRoleDtoCollection(roleRepository.findAll());
    }
}
