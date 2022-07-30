package com.crud.springbootcrud.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String company;
    private String avatar;
    private String jobTitle;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", nullable = false)
    @ToString.Exclude
    private Role role;

}
