package com.example.myTestApp.service.updatedevaluator.lexer.lexeme;
public record Lexeme(Double operand, OperationType operationType) {
    public Lexeme(OperationType operationType) {
        this(null, operationType);
    }
    public Lexeme(Double operand) {
        this(operand, OperationType.OPERAND);
    }
}