package com.example.myTestApp.service.evaluator;

import com.example.myTestApp.exception.BadOperandException;
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
        var expression = "2    --2  + -2 -+2 ++2";
        var res = handler.removeDoubleSymbolsAndSpaces(expression);
        assertEquals(res, "2+2-2-2+2");
    }

    @Test
    void getSecondOperandEndIndex() {
        var operation = "2+2";
        var res = handler.getSecondOperandEndIndex(operation, 1);
        assertEquals(res, 3);

        operation = "2+2+2";
        res = handler.getSecondOperandEndIndex(operation,1);
        assertEquals(res, 3);

        operation = "2+2.5+2";
        res = handler.getSecondOperandEndIndex(operation,1);
        assertEquals(res, 5);
    }

    @Test
    void getFirstOperandStartIndex() {
        var operation = "2+2";
        var res = handler.getFirstOperandStartIndex(operation, 1);
        assertEquals(res, 0);

        operation = "2+2+2";
        res = handler.getFirstOperandStartIndex(operation,3);
        assertEquals(res, 2);

        operation = "2-2+2";
        res = handler.getFirstOperandStartIndex(operation,3);
        assertEquals(res, 1);

        operation = "2+2.5+2";
        res = handler.getFirstOperandStartIndex(operation,5);
        assertEquals(res, 2);
    }

    @Test
    void getCheckIfExpressionDoesntMissedBrackets() {
        var expression = "(())";
        handler.checkIfExpressionDoesntMissedBrackets(expression);

        expression = "()(";

    }

}