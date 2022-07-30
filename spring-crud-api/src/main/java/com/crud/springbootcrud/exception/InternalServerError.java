package com.crud.springbootcrud.exception;

public class InternalServerError extends RuntimeException{

    public InternalServerError(String message) {
        super(message);
    }
}
