package com.example.myTestApp.service.updatedevaluator.lexer;

import org.junit.jupiter.api.Test;

class LexerTest {
    private final Lexer lexer = new LexerImpl();

    @Test
    void toLexemeList() {
        var expression = "5*+1(+2.55) +4";
        var res = lexer.tokenize(expression);
        System.out.println(res);
    }
}