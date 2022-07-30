package com.crud.springbootcrud.service;

import com.crud.springbootcrud.exception.BadRequestException;
import com.crud.springbootcrud.exception.InternalServerError;
import com.crud.springbootcrud.exception.UserNotFoundException;
import com.crud.springbootcrud.model.User;
import com.crud.springbootcrud.model.dto.UserDto;
import com.crud.springbootcrud.model.dto.UserRegistrationDto;
import com.crud.springbootcrud.repository.UserRepository;
import com.crud.springbootcrud.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserDto save(UserRegistrationDto userRegistrationDto) {
        if (existByEmail(userRegistrationDto.getEmail())) {
            throw new BadRequestException("User with email: " + userRegistrationDto.getEmail() + " already exist");
        }
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getRePassword())) {
            throw new BadRequestException("Password and repeat password must be the same");
        }
        User user = userMapper.toUser(userRegistrationDto);
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRole(roleService.getByName("ROLE_USER"));
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        if (page < 0 || size < 0) throw new BadRequestException("page and size must not be less than zero");
        Pageable pageable = PageRequest.of(page, size);
        return userMapper.toUserDtoList(userRepository.findAll(pageable).toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id: " + id + " not found")));
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new UserNotFoundException("User with id: " + userDto.getId() + " not found"));
        if (userDto.getRole() != null) {
            user.setRole(roleService.getByName(userDto.getRole().getName()));
        }
        userMapper.updateFromUserDto(userDto, user);
        return userMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        }catch (Exception e) {
            throw new InternalServerError("Internal server error user delete please try again");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UserNotFoundException("User with email " + username + " not found"));
        Collection<GrantedAuthority> grantedAuthority =
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthority);
    }

    private boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
