package com.example.myTestApp.service.lexer;

import com.example.myTestApp.service.lexer.Lexer;
import com.example.myTestApp.service.lexer.LexerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexerTest {
    private final Lexer lexer = new LexerImpl();

    @Test
    void toLexemeList() {
        var expression = "2.5+44/(2--2)+ 3.5";
        var res = lexer.tokenize(expression);
        assertEquals(res.toString(), "[2.5, ADDITION, 44.0, DIVISION, (, 2.0, SUBTRACTION, -2.0, ), ADDITION, 3.5]");

        expression = "2+3+(2+((2+2))) -6";
        res = lexer.tokenize(expression);
        assertEquals(res.toString(), "[2.0, ADDITION, 3.0, ADDITION, (, 2.0, ADDITION, (, (, 2.0, ADDITION, 2.0, ), ), ), SUBTRACTION, 6.0]");

        expression = "2++2 -+2+-2";
        res = lexer.tokenize(expression);
        assertEquals(res.toString(), "[2.0, ADDITION, 2.0, SUBTRACTION, 2.0, ADDITION, -2.0]");
    }
}