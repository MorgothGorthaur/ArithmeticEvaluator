package com.example.myTestApp.service.updatedevaluator.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    private final Lexer lexer = new LexerImpl();

    @Test
    void toLexemeList() {
        var expression = "5*+1(+) +4";
        var res = lexer.toLexemeList(expression);
        System.out.println(res);
    }
}