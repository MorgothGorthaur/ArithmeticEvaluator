package com.example.myTestApp.exception;

public class DivisionByZeroException extends RuntimeException{
    public DivisionByZeroException() {
        super("division by zero!");
    }
}
