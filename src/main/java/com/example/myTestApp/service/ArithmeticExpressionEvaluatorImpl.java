package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import org.springframework.stereotype.Service;

@Service
public class ArithmeticExpressionEvaluatorImpl implements ArithmeticExpressionEvaluator {
    @Override
    public double getEvaluation(String arithmeticExpression) {
        arithmeticExpression = simplifyExpression(check(arithmeticExpression));
        var res = evaluation(arithmeticExpression);
        return getOperand(res, 0, res.length());

    }
    private String check(String string) {
        if (string.charAt(0) == '+') string = string.substring(1);
        return string.strip().replaceAll("\\ ", "")
                .replaceAll("\\-\\-", "+")
                .replaceAll("\\+\\-", "-")
                .replaceAll("\\-\\+", "-")
                .replaceAll("\\+\\+", "+");
    }
    private String simplifyExpression(String expression) {
        var bracketIndexes = getBracketsIndexes(expression);
        var firstExpression = expression.substring(0, bracketIndexes[0]);
        var lastExpression = expression.substring(bracketIndexes[1] + 1);
        var subExpression = checkIfExpressionContainsBrackets(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1]))
                 ? simplifyExpression(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1]))
                : String.valueOf(evaluation(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1])));
        var res = firstExpression + subExpression + lastExpression;
        return checkIfExpressionContainsBrackets(res)? simplifyExpression(check(res)) : check(res);
    }
    private int[] getBracketsIndexes(String expression) {
        var firstBracketIndex = expression.indexOf("(");
        var lastBracketIndex = firstBracketIndex +1;
        if(expression.indexOf(")") < firstBracketIndex) {
            throw new MissedLeftBracketException();
        }
        while (lastBracketIndex < expression.length() && expression.charAt(lastBracketIndex) != ')') {
            if(expression.charAt(lastBracketIndex) == '('){
                firstBracketIndex = lastBracketIndex;
            }
            lastBracketIndex ++;
        }
        if(expression.charAt(lastBracketIndex) != ')') {
            throw new MissedRightBracketException();
        }
        return new int[] {firstBracketIndex, lastBracketIndex};
    }
    private String evaluation(String string) {
        var opIndex = 0;
        var operationType = OperationType.DOESNT_SETTED;
        if (string.contains("*")) {
            opIndex = string.indexOf("*");
            operationType = OperationType.MULTIPLICATION;
        } else if (string.contains("/")) {
            opIndex = string.indexOf("/");
            operationType = OperationType.DIVISION;
        } else if (string.contains("+")) {
            opIndex = string.indexOf("+");
            operationType = OperationType.ADDITION;
        } else if (string.contains("-")) {
            opIndex = string.indexOf("-");
            if (opIndex != 0) {
                operationType = OperationType.SUBTRACTION;
            }
        }
        if (!operationType.equals(OperationType.DOESNT_SETTED)) {
            string = evaluation(doOperation(operationType, opIndex, string));
        }
        return string;
    }
    private String doOperation(OperationType operationType, int opIndex, String expression) {
        var indexes = getOperandValuesIndexes(expression, opIndex);
        var firstOperand = indexes[0] == -1 ? 0 :getOperand(expression, indexes[0], opIndex);
        var lastOperand = getOperand(expression, opIndex+1, indexes[1]);
        var res = indexes[0] == -1 ? "" : expression.substring(0, indexes[0]);
        res += "+";
        switch (operationType) {
            case MULTIPLICATION -> res += firstOperand * lastOperand;
            case DIVISION -> res += firstOperand / lastOperand;
            case SUBTRACTION -> res += (firstOperand - lastOperand);
            case ADDITION -> res += (firstOperand + lastOperand);
            default -> throw new RuntimeException("unknown operation type!");
        }
        res += expression.substring(indexes[1]);
        return check(res);
    }
    private int[] getOperandValuesIndexes(String expression, int opIndex) {
        var firstIndex = opIndex - 1;
        var lastIndex = opIndex + 1;
        while (firstIndex - 1 > -1 && (expression.charAt(firstIndex - 1) == '.' || Character.isDigit(expression.charAt(firstIndex - 1)))) {
            firstIndex--;
        }
        if (firstIndex - 1 > -1 && expression.charAt(firstIndex - 1) == '-') {
            firstIndex--;
        }
        while (lastIndex + 1 < expression.length() && (expression.charAt(lastIndex + 1) == '.' || Character.isDigit(expression.charAt(lastIndex + 1)))) {
            lastIndex++;
        }
        return new int[]{firstIndex, lastIndex + 1};
    }
    private boolean checkIfExpressionContainsBrackets(String exp) {
        return exp.contains("(") && exp.contains(")");
    }

    private double getOperand(String expression, int firstIndex, int lastIndex){
        try {
            return Double.parseDouble(expression.substring(firstIndex, lastIndex));
        } catch (RuntimeException ex) {
            throw new BadOperandException();
        }
    }

}
