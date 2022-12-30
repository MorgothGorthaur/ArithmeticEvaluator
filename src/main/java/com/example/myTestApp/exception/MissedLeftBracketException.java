package com.example.myTestApp.exception;

public class MissedLeftBracketException extends RuntimeException{
    public MissedLeftBracketException() {
        super("Missed (");
    }
}
