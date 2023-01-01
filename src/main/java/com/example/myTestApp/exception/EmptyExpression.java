package com.example.myTestApp.exception;

public class EmptyExpression extends RuntimeException{
    public EmptyExpression() {
        super("empty expression");
    }
}
