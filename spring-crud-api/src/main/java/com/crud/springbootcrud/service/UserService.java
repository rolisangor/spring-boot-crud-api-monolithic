package com.crud.springbootcrud.service;

import com.crud.springbootcrud.model.dto.UserDto;
import com.crud.springbootcrud.model.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto save(UserRegistrationDto userRegistrationDto);
    List<UserDto> getAllUsers(int page, int size);
    UserDto getUserById(Long id);
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long id);

}
