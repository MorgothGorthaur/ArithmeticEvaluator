package com.example.myTestApp.exception;

public class EmptyExpressionException extends RuntimeException{
    public EmptyExpressionException() {
        super("empty expression");
    }
}
