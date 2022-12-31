package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor
class StringExpressionHandlerTest {
    final StringExpressionHandler handler = new StringExpressionHandlerImpl();
    @Test
    void splitByBrackets() {
        var expression = "2(2+2)+3";
        var res = handler.splitByBrackets(expression);
        assertEquals(res[0], "2*");
        assertEquals(res[1], "2+2");
        assertEquals(res[2], "+3");

        expression = "2(2/(3+2)) +5";
        res = handler.splitByBrackets(expression);
        assertEquals(res[0], "2(2/");
        assertEquals(res[1], "3+2");
        assertEquals(res[2], ") +5");

        expression = "()";
        res = handler.splitByBrackets(expression);
        assertEquals(res[0], "");
        assertEquals(res[1], "");
        assertEquals(res[2], "");
    }

    @Test
    void getOperand() {
        var operation = "2+2";
        var firstOperand = handler.getOperand(operation, 0, 1);
        var lastOperand = handler.getOperand(operation,2,3);
        assertEquals(firstOperand, 2.0);
        assertEquals(lastOperand, 2.0);
    }
    @Test
    void getOperand_shouldThrowBadOperandException() {
        var operation = "2+2";
        var exception = assertThrows(BadOperandException.class, () -> {
            handler.getOperand(operation, 0, 2);
        });
        assertEquals(exception.getMessage(), "bad operand");


    }

    @Test
    void removeDoubleSymbolsAndSpaces() {
    }

    @Test
    void getOperandEndIndex() {
    }

    @Test
    void getOperandStartIndex() {
    }

    @Test
    void checkIfExpressionContainsBrackets() {
    }
}