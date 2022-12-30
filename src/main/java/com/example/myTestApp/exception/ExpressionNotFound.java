package com.example.myTestApp.exception;

public class ExpressionNotFound extends RuntimeException{
    public ExpressionNotFound(Long id) {
        super("expression not found! id = " + id);
    }
}
