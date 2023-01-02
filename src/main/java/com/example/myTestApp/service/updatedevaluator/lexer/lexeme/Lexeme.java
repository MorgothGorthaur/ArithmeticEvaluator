package com.example.myTestApp.service.updatedevaluator.lexer.lexeme;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Lexeme {
    @Getter @Setter
    private Double operand;
    private final OperationType operationType;
}
