package com.example.myTestApp.exception;

public class BadOperandException extends RuntimeException{
    public BadOperandException() {
        super("bad operand");
    }
}
