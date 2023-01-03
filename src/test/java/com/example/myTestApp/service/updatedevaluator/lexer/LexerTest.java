package com.example.myTestApp.service.updatedevaluator.lexer;

import org.junit.jupiter.api.Test;

class LexerTest {
    private final Lexer lexer = new LexerImpl();

    @Test
    void toLexemeList() {
        var expression = "2.5+44/(2--2)+ 3.5";
        var res = lexer.tokenize(expression);
        System.out.println(res);
    }
}