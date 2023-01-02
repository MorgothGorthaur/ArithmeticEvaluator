package com.example.myTestApp.service.updatedevaluator.lexer.lexeme;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class Lexeme {
    private Double operand;
    private final OperationType operationType;
}
