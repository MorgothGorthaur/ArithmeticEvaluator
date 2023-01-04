package com.example.myTestApp.service.parser;

import com.example.myTestApp.exception.*;
import com.example.myTestApp.service.token.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private final Parser parser = new ParserImpl();


    @Test
    void evaluate() {


        //2+3*(2*(6+3))
        var tokens = new LinkedList<Token>();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(6.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        tokens.add(new BracketToken(false));
        tokens.add(new BracketToken(false));
        var res = parser.evaluate(tokens);
        assertEquals(res, 56.0);
        //(2+3*2)
        tokens.clear();
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new BracketToken(false));
        res = parser.evaluate(tokens);
        assertEquals(res, 8);
        //((2+3*(4* 5)))
        tokens.clear();
        tokens.add(new BracketToken(true));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(4.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new NumberToken(5.0));
        tokens.add(new BracketToken(false));
        tokens.add(new BracketToken(false));
        tokens.add(new BracketToken(false));
        res = parser.evaluate(tokens);
        assertEquals(res, 62);
        //2+2*2
        tokens.clear();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new NumberToken(2.0));
        res = parser.evaluate(tokens);
        assertEquals(res, 6);
//2+44/(2+2)+3.5
        tokens.clear();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(44.0));
        tokens.add(new OperationToken(OperationType.DIVISION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new BracketToken(false));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.5));
        res = parser.evaluate(tokens);
        assertEquals(res, 16.5);
        //2+3+(2+((2+2))) -6
        tokens.clear();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new BracketToken(true));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new BracketToken(false));
        tokens.add(new BracketToken(false));
        tokens.add(new BracketToken(false));
        tokens.add(new OperationToken(OperationType.SUBTRACTION));
        tokens.add(new NumberToken(6.0));
        res = parser.evaluate(tokens);
        assertEquals(res, 5.0);

        //2+2 -2 + -2
        tokens.clear();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.SUBTRACTION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(-2.0));
        res = parser.evaluate(tokens);
        assertEquals(res, 0);
    }
    @Test
    void evaluate_shouldThrowEmptyExpressionException() {
        //2*()
        var tokens = new LinkedList<Token>();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new BracketToken(false));
        var exception = assertThrows(EmptyExpressionException.class, () -> {
            parser.evaluate(tokens);
        });
        assertEquals(exception.getMessage(), "empty expression");
        //
        tokens.clear();
        var secondException = assertThrows(EmptyExpressionException.class, () ->{
            parser.evaluate(tokens);
        });
        assertEquals(secondException.getMessage(), "empty expression");
    }

    @Test
    void evaluate_shouldThrowMissedLeftBracketException() {
        //)
        var tokens = new LinkedList<Token>();
        tokens.add(new BracketToken(false));
        var exception = assertThrows(MissedLeftBracketException.class, () ->{
            parser.evaluate(tokens);
        });
        assertEquals(exception.getMessage(), "Missed (");
        //2*(3+2)+2)
        tokens.clear();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(3.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new BracketToken(false));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new BracketToken(false));
        var secondException = assertThrows(MissedLeftBracketException.class, () ->{
            parser.evaluate(tokens);
        });
        assertEquals(secondException.getMessage(), "Missed (");
    }
    @Test
    void evaluate_shouldThrowOperationExpectedException() {
        //2 2
        var tokens = new LinkedList<Token>();
        tokens.add(new NumberToken(2.0));
        tokens.add(new NumberToken(2.0));
        var expression = assertThrows(OperationExpectedException.class, () -> {
            parser.evaluate(tokens);
        });
        assertEquals(expression.getMessage(), "operation expected!");
    }
    @Test
    void evaluate_shouldThrowDivisionByZeroException() {
        //2+(4-4)+3
        var tokens = new LinkedList<Token>();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.DIVISION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(4.0));
        tokens.add(new OperationToken(OperationType.SUBTRACTION));
        tokens.add(new NumberToken(4.0));
        tokens.add(new BracketToken(false));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        var expression = assertThrows(DivisionByZeroException.class, () -> {
           parser.evaluate(tokens);
        });
        assertEquals(expression.getMessage(), "division by zero!");
    }
    @Test
    void evaluate_shouldThrowBadOperandException() {
        //2 + + (2+2)
        var tokens = new LinkedList<Token>();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(2.0));
        tokens.add(new BracketToken(false));
        var expression = assertThrows(BadOperandException.class, () -> {
            parser.evaluate(tokens);
        });
        assertEquals(expression.getMessage(), "bad operand");
    }
    @Test
    void getNumOfOperands() {
    }
}