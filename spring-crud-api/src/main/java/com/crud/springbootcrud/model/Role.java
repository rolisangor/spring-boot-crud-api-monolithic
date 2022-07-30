package com.crud.springbootcrud.model;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Table(name = "role")
@Entity
public class Role extends BaseEntity{

    private String name;
}
