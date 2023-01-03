package com.example.myTestApp.service.lexer;

import com.example.myTestApp.service.lexer.Lexer;
import com.example.myTestApp.service.lexer.LexerImpl;
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