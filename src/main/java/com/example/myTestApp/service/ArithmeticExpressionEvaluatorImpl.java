package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ArithmeticExpressionEvaluatorImpl implements ArithmeticExpressionEvaluator {
    private final StringExpressionHandler handler;
    @Override
    public double getEvaluation(String arithmeticExpression) {
        if (handler.checkIfExpressionContainsBrackets(arithmeticExpression)) {
            arithmeticExpression = simplifyExpression(handler.removeDoubleSymbolsAndSpaces(arithmeticExpression));
        }
        var res = evaluateExpression(handler.removeDoubleSymbolsAndSpaces(arithmeticExpression));
        return handler.getOperand(res, 0, res.length());
    }
    private String simplifyExpression(String expression) {
        var bracketIndexes = handler.getBracketIndexes(expression);
        if (bracketIndexes[0] - 1 >= 0 && Character.isDigit(expression.charAt(bracketIndexes[0] - 1))) {
            expression = expression.substring(0, bracketIndexes[0]) + '*' + expression.substring(bracketIndexes[0]);
            bracketIndexes[0] += 1;
            bracketIndexes[1] += 1;
        }
        var subExpression =  String.valueOf(evaluateExpression(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1])));
        var res = handler.removeDoubleSymbolsAndSpaces(expression.substring(0, bracketIndexes[0]) + subExpression +  expression.substring(bracketIndexes[1] + 1));
        return handler.checkIfExpressionContainsBrackets(res) ? simplifyExpression(res) : res;
    }
    private String evaluateExpression(String expression) {
        var opIndexes = new int[]{expression.indexOf('*'), expression.indexOf('/'), expression.indexOf('+'), expression.lastIndexOf('-')};
        if ((opIndexes[0] <= opIndexes[1] || opIndexes[1] == -1) && opIndexes[0] != -1) {
            expression = evaluateExpression(doOperation(OperationType.MULTIPLICATION, opIndexes[0], expression));
        } else if (opIndexes[1] != -1) {
            expression = evaluateExpression(doOperation(OperationType.DIVISION, opIndexes[1], expression));
        } else if (opIndexes[2] != -1) {
            expression = evaluateExpression(doOperation(OperationType.ADDITION, opIndexes[2], expression));
        } else if (opIndexes[3] > 0) {
            expression = evaluateExpression(doOperation(OperationType.SUBTRACTION, opIndexes[3], expression));
        }
        return expression;
    }

    private String doOperation(OperationType operationType, int opIndex, String expression) {
        var leftOperandStartIndex = handler.getOperandStartIndex(expression, opIndex);
        var rightOperandEndIndex = handler.getOperandEndIndex(expression, opIndex);
        var res = expression.substring(0, leftOperandStartIndex) + "+";
        res += operationType.doOperation(handler.getOperand(expression, leftOperandStartIndex, opIndex), handler.getOperand(expression, opIndex + 1, rightOperandEndIndex)) + expression.substring(rightOperandEndIndex);
        return handler.removeDoubleSymbolsAndSpaces(res);
    }
}
