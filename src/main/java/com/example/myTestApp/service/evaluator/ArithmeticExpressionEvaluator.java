package com.example.myTestApp.service.evaluator;

import org.springframework.stereotype.Service;

@Service
public interface ArithmeticExpressionEvaluator {
    double getEvaluation(String arithmeticExpression);
}
