package com.example.myTestApp.service.evaluator;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.DivisionByZeroException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticExpressionEvaluatorImplTest {
    final StringExpressionHandler handler = new StringExpressionHandlerImpl();
    final ArithmeticExpressionEvaluator arithmeticExpressionEvaluator = new ArithmeticExpressionEvaluatorImpl(handler);

    @Test
    void getEvaluation_basicArithmeticOperations() {
        var expression = "2+2";
        var res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 4.0);

        expression = "0.5 + 3.5";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 4.0);

        expression = "2 - 3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, -1.0);

        expression = "-2 + 3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 1.0);

        expression = "-2 + -3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, -5.0);

        expression = "2 + -3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, -1.0);

        expression = "2 -- 2";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 4.0);

        expression = "2 * -3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, -6.0);

        expression = "2 / 3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 0.667);
        expression = "3 / 2";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 1.5);

        expression = "2";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 2.0);
    }

    @Test
    void getEvaluation_basicArithmeticOperationsWithMoreThenTwoOperands() {
        var expression = "2+2+2";
        var res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 6.0);

        expression = "2+2*3";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 8.0);

        expression = "2 * 2 /4";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 1.0);

        expression = "2/2*4";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 4.0);

        expression = "2.5/--2.5 *--4";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 4.0);
    }

    @Test
    void getEvaluation_basicArithmeticOperationsWithBrackets() {
        var expression = "2(2+2)";
        var res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 8.0);

        expression = "2 (2* ((2 + 2)) / -10)";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, -1.6);

        expression = "31   + (2* -(2 + -6*2)) +  -4 --6 + (2+2)";
        res = arithmeticExpressionEvaluator.getEvaluation(expression);
        assertEquals(res, 57.0);
    }

    @Test
    void getEvaluation_shouldThrowDivisionByZeroException() {
        var expression = "2/0";
        var exception = assertThrows(DivisionByZeroException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(expression);
        });
        assertEquals(exception.getMessage(), "division by zero!");

        var secondExpression = "(2+2) / (4 * 0) + 66";
        exception = assertThrows(DivisionByZeroException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(secondExpression);
        });
        assertEquals(exception.getMessage(), "division by zero!");

    }

    @Test
    void getEvaluation_shouldThrowBadOperandException() {
        var expression = "2+*2";
        var exception = assertThrows(BadOperandException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(expression);
        });
        assertEquals(exception.getMessage(), "bad operand");

        var secondExpression = "2 + abracadabra + 3";
        exception = assertThrows(BadOperandException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(secondExpression);
        });
        assertEquals(exception.getMessage(), "bad operand");
    }
@Test
void getEvaluation_shouldThrow
    @Test
    void getEvaluation_shouldThrowMissedLeftBracketException() {
        var expression = "2 + (3 + 5 *7))";
        var exception = assertThrows(MissedLeftBracketException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(expression);
        });
        assertEquals(exception.getMessage(), "Missed (");

        var secondExpression = ")";
        exception = assertThrows(MissedLeftBracketException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(secondExpression);
        });
        assertEquals(exception.getMessage(), "Missed (");
    }

    @Test
    void getEvaluation_shouldThrowRightBracketException() {
        var expression = "2 + ((3 + 5 *7)";
        var exception = assertThrows(MissedRightBracketException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(expression);
        });
        assertEquals(exception.getMessage(), "Missed )");

        var secondExpression = "(";
        exception = assertThrows(MissedRightBracketException.class, () -> {
            arithmeticExpressionEvaluator.getEvaluation(secondExpression);
        });
        assertEquals(exception.getMessage(), "Missed )");
    }

}