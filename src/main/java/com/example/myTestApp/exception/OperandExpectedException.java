package com.example.myTestApp.exception;

public class OperandExpectedException extends RuntimeException{
    public OperandExpectedException() {
        super("operand expected!");
    }
}
