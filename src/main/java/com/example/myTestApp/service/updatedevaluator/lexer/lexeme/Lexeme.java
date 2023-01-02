package com.example.myTestApp.service.updatedevaluator.lexer.lexeme;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Lexeme {
    private Double operand;
    private final OperationType operationType;
}
