package com.example.myTestApp.exception;

public class MissedRightBracketException extends RuntimeException {
    public MissedRightBracketException() {
        super("Missed )");
    }
}
