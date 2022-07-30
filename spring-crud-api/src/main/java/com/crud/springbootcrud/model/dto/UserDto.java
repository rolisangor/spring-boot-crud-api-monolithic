package com.crud.springbootcrud.model.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    @NotNull(message = "firstName must be present")
    @NotBlank(message = "firstName must be present")
    private String firstName;

    @NotNull(message = "lastName must be present")
    @NotBlank(message = "firstName must be present")
    private String lastName;

    @NotNull(message = "email must be present")
    @NotBlank(message = "email must be present")
    private String email;

    private String gender;
    private String company;
    private String avatar;
    private String jobTitle;
    private Instant createdAt;
    private RoleDto role;

}
