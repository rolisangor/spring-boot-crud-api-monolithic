package com.crud.springbootcrud.controller.advice;

import com.crud.springbootcrud.exception.BadRequestException;
import com.crud.springbootcrud.exception.InternalServerError;
import com.crud.springbootcrud.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("METHOD_NOT_VALID_EXCEPTION_HANDLE_MESSAGE: {}",
                Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
        return getErrorBody(exception.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMethodArgumentNotValidException(MissingServletRequestParameterException exception) {
        log.error("MISSING_REQUEST_PARAM_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleMethodArgumentNotValidException(BadRequestException exception) {
        log.error("BAD_REQUEST_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleConstraintViolation(ConstraintViolationException exception) {
        log.error("VALIDATION_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        final List<String> validationErrors = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ":" + violation.getMessage())
                .collect(Collectors.toList());
        return getErrorBody(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleMethodArgumentNotValidException(UserNotFoundException exception) {
        log.error("USER_NOT_FOUND_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({Exception.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseError handleAllExceptions(Exception exception) {
//        log.error("DEFAULT_EXCEPTION_HANDLE_MESSAGE: {}", exception.getMessage());
//        return getErrorBody("Internal server error please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler({InternalServerError.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleAllExceptions(InternalServerError exception) {
        log.error("INTERNAL_SERVER_ERROR_HANDLE_MESSAGE: {}", exception.getMessage());
        return getErrorBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseError getErrorBody(String message, HttpStatus status) {
        return ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .errors(List.of(message))
                .status(status.value()).build();
    }

    private ResponseError getErrorBody(List<String> message, HttpStatus status) {
        return ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .errors(message)
                .status(status.value()).build();
    }

}
