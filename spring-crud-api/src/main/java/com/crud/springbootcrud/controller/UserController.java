package com.crud.springbootcrud.controller;

import com.crud.springbootcrud.model.dto.UserDto;
import com.crud.springbootcrud.model.dto.UserRegistrationDto;
import com.crud.springbootcrud.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/principal")
    public ResponseEntity<String> getPrincipal(Authentication authentication) {
        log.info("AUTHENTICATION_NAME: {}", authentication.getName());
        return ResponseEntity.ok("Get principal success");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(userService.save(userRegistrationDto));
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successful");
    }
}
