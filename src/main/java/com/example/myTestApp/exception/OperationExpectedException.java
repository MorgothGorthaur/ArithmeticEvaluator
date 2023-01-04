package com.example.myTestApp.exception;

public class OperationExpectedException extends RuntimeException{
    public OperationExpectedException() {
        super("operation expected!");
    }
}
