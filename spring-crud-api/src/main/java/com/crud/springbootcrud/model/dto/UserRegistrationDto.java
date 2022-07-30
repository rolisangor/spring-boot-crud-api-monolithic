package com.crud.springbootcrud.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserRegistrationDto {

    private String email;
    private String password;
    private String rePassword;

}
