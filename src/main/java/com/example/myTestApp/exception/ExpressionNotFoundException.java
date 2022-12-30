package com.example.myTestApp.exception;

public class ExpressionNotFoundException extends RuntimeException{
    public ExpressionNotFoundException(Long id) {
        super("expression not found! id = " + id);
    }
}
