package com.example.myTestApp.service;

import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ArithmeticExpressionEvaluatorImpl implements ArithmeticExpressionEvaluator {
    private final StringExpressionHandler handler;

    @Override
    public double getEvaluation(String arithmeticExpression) {
        if (handler.checkIfExpressionContainsBrackets(arithmeticExpression)) {
            handler.checkIfExpressionDoesntMissedBrackets(arithmeticExpression);
            arithmeticExpression = simplifyExpression(handler.removeDoubleSymbolsAndSpaces(arithmeticExpression));
        }
        var res = evaluateExpression(handler.removeDoubleSymbolsAndSpaces(arithmeticExpression));
        return handler.getOperand(res, 0, res.length());
    }

    private String simplifyExpression(String expression) {
        var subExpressions = handler.splitByBrackets(expression);
        var res = handler.removeDoubleSymbolsAndSpaces(subExpressions[0] + evaluateExpression(subExpressions[1]) + subExpressions[2]);
        return handler.checkIfExpressionContainsBrackets(res) ? simplifyExpression(res) : res;
    }

    private String evaluateExpression(String expression) {
        var operationTypeIndexes = new int[]{expression.indexOf('*'), expression.indexOf('/'), expression.indexOf('+'), expression.lastIndexOf('-')};
        if ((operationTypeIndexes[0] <= operationTypeIndexes[1] || operationTypeIndexes[1] == -1) && operationTypeIndexes[0] != -1) {
            expression = evaluateExpression(doOperation(OperationType.MULTIPLICATION, operationTypeIndexes[0], expression));
        } else if (operationTypeIndexes[1] != -1) {
            expression = evaluateExpression(doOperation(OperationType.DIVISION, operationTypeIndexes[1], expression));
        } else if (operationTypeIndexes[2] != -1) {
            expression = evaluateExpression(doOperation(OperationType.ADDITION, operationTypeIndexes[2], expression));
        } else if (operationTypeIndexes[3] > 0) {
            expression = evaluateExpression(doOperation(OperationType.SUBTRACTION, operationTypeIndexes[3], expression));
        }
        return expression;
    }

    private String doOperation(OperationType operationType, int opIndex, String operation) {
        var leftOperandStartIndex = handler.getOperandStartIndex(operation, opIndex);
        var rightOperandEndIndex = handler.getOperandEndIndex(operation, opIndex);
        var firstOperand = handler.getOperand(operation, leftOperandStartIndex, opIndex);
        var lastOperand = handler.getOperand(operation, opIndex + 1, rightOperandEndIndex);
        var res = operation.substring(0, leftOperandStartIndex) + "+";
        res += operationType.doOperation(firstOperand, lastOperand) + operation.substring(rightOperandEndIndex);
        return handler.removeDoubleSymbolsAndSpaces(res);
    }

}
