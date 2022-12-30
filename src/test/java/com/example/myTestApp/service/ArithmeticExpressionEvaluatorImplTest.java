package com.example.myTestApp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ArithmeticExpressionEvaluatorImplTest {
    final ArithmeticExpressionEvaluator arithmeticExpressionEvaluator = new ArithmeticExpressionEvaluatorImpl();
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


    }
}