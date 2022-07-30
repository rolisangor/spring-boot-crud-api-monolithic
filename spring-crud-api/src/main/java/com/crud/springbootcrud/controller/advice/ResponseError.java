package com.crud.springbootcrud.controller.advice;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ResponseError {

    List<String> errors;
    private LocalDateTime timestamp;
    private int status;

}
