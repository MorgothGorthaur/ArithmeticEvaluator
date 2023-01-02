package com.example.myTestApp.service.updatedevaluator.parser;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedRightBracketException;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.Lexeme;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.OperationType;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private final Parser parser = new ParserImpl();

    @Test
    void getResult_shouldThrowMissedRightBracketException() {
        var lst = new LinkedList<Lexeme>();
        lst.add(new Lexeme(OperationType.LEFT_BRACKET));
        lst.add(new Lexeme(2.0));
        var exception = assertThrows(MissedRightBracketException.class, () -> {
            parser.getResult(lst);
        });
        assertEquals(exception.getMessage(), "Missed )");
    }

    @Test
    void getResult_shouldThrowBadOperandException() {
        var lst = new LinkedList<Lexeme>();
        lst.add(new Lexeme(3.0));
        lst.add(new Lexeme(OperationType.ADDITION));
        lst.add(new Lexeme(OperationType.MULTIPLICATION));
        lst.add(new Lexeme(2.0));
        var exception = assertThrows(BadOperandException.class, () -> {
            parser.getResult(lst);
        });
        assertEquals(exception.getMessage(), "bad operand");
    }

    @Test
    void getNumOfOperands() {
        var lst = new LinkedList<Lexeme>();
        lst.add(new Lexeme(OperationType.LEFT_BRACKET));
        lst.add(new Lexeme(3D));
        var res = parser.getNumOfOperands(lst);
        assertEquals(res, 1);
    }
}