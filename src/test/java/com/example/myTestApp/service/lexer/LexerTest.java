package com.example.myTestApp.service.lexer;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.service.lexer.Lexer;
import com.example.myTestApp.service.lexer.LexerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LexerTest {
    private final Lexer lexer = new LexerImpl();

    @Test
    void tokenize() {
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

    @Test
    void tokenize_shouldThrowBadOperandException() {
        var expression = "3p4";
        assertThrows(BadOperandException.class, () -> {
           lexer.tokenize(expression);
        });
        var secondExpression = "3+p";
        assertThrows(BadOperandException.class, () -> {
            lexer.tokenize(secondExpression);
        });
    }
}