package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import org.springframework.stereotype.Service;

@Service
class ArithmeticExpressionEvaluatorImpl implements ArithmeticExpressionEvaluator {
    @Override
    public double getEvaluation(String arithmeticExpression) {
        if (checkIfExpressionContainsBrackets(arithmeticExpression)) {
            arithmeticExpression = simplifyExpression(removeDoubleSymbolsAndSpaces(arithmeticExpression));
        }
        var res = evaluateExpression(removeDoubleSymbolsAndSpaces(arithmeticExpression));
        return getOperand(res, 0, res.length());
    }
    private String simplifyExpression(String expression) {
        var bracketIndexes = getBracketsIndexes(expression);
        if (bracketIndexes[0] - 1 >= 0 && Character.isDigit(expression.charAt(bracketIndexes[0] - 1))) {
            expression = expression.substring(0, bracketIndexes[0]) + '*' + expression.substring(bracketIndexes[0]);
            bracketIndexes[0] += 1;
            bracketIndexes[1] += 1;
        }
        var subExpression =  String.valueOf(evaluateExpression(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1])));
        var res = removeDoubleSymbolsAndSpaces(expression.substring(0, bracketIndexes[0]) + subExpression +  expression.substring(bracketIndexes[1] + 1));
        return checkIfExpressionContainsBrackets(res) ? simplifyExpression(res) : res;
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
        var indexes = getOperandValuesIndexes(expression, opIndex);
        var firstOperand = getOperand(expression, indexes[0], opIndex);
        var lastOperand = getOperand(expression, opIndex + 1, indexes[1]);
        var res = indexes[0] == -1 ? "" : expression.substring(0, indexes[0]);
        res += "+" + operationType.doOperation(firstOperand, lastOperand) + expression.substring(indexes[1]);

        return removeDoubleSymbolsAndSpaces(res);
    }

    private String removeDoubleSymbolsAndSpaces(String string) {
        if (string.length() >0 && string.charAt(0) == '+') string = string.substring(1);
        return string.strip().replaceAll("\\ ", "")
                .replaceAll("--", "+")
                .replaceAll("\\+-", "-")
                .replaceAll("-\\+", "-")
                .replaceAll("\\++", "+");
    }
    private int[] getOperandValuesIndexes(String expression, int opIndex) {
        var firstIndex = getFirstOperandIndex(expression, opIndex);
        int lastIndex = getLastOperandIndex(expression, opIndex);
        return new int[]{firstIndex, lastIndex + 1};
    }

    private int getLastOperandIndex(String expression, int opIndex) {
        var lastIndex = opIndex + 1;
        while (lastIndex + 1 < expression.length() && (expression.charAt(lastIndex + 1) == '.' || Character.isDigit(expression.charAt(lastIndex + 1)))) {
            lastIndex++;
        }
        return lastIndex;
    }

    private boolean checkIfExpressionContainsBrackets(String exp) {
        return exp.contains("(") && exp.contains(")");
    }
    private double getLeftOperand(String expression, int endIndex) {
        var firstIndex = endIndex -1;
        while (firstIndex > 0  && (expression.charAt(firstIndex - 1) == '.' || Character.isDigit(expression.charAt(firstIndex - 1)))) {
            firstIndex--;
        }
        firstIndex = firstIndex > 0 && expression.charAt(firstIndex-1) == '-' ? firstIndex-1 : firstIndex;
        return getOperand(expression, firstIndex, endIndex);
    }
    private double getRightOperand(String expression, int firstIndex) {
        var endIndex = firstIndex + 1;
        while (endIndex + 1 < expression.length() && (expression.charAt(endIndex + 1) == '.' || Character.isDigit(expression.charAt(endIndex + 1)))) {
            endIndex++;
        }
        return getOperand(expression, firstIndex, endIndex);
    }
    private int getFirstOperandIndex(String expression, int opIndex) {
        var firstIndex = opIndex - 1;
        while (firstIndex > 0  && (expression.charAt(firstIndex - 1) == '.' || Character.isDigit(expression.charAt(firstIndex - 1)))) {
            firstIndex--;
        }
        return firstIndex > 0 && expression.charAt(firstIndex-1) == '-' ? firstIndex-1 : firstIndex;
    }

    private double getOperand(String expression, int firstIndex, int lastIndex) {
        if (expression.contains("(")) throw new MissedRightBracketException();
        if (expression.contains(")")) throw new MissedLeftBracketException();
        if(expression.equals("")) return 0;
        try {
            return Double.parseDouble(expression.substring(firstIndex, lastIndex));
        } catch (RuntimeException ex) {
            throw new BadOperandException();
        }
    }

    private void checkIfRightBracketIsMissed(String expression, int lastBracketIndex) {
        if (expression.charAt(lastBracketIndex) != ')') {
            throw new MissedRightBracketException();
        }
    }

    private void checkIfLeftBracketIsMissed(String expression, int firstBracketIndex) {
        if (expression.indexOf(")") < firstBracketIndex) {
            throw new MissedLeftBracketException();
        }
    }
    private int[] getBracketsIndexes(String expression) {
        var firstBracketIndex = expression.indexOf("(");
        var lastBracketIndex = firstBracketIndex + 1;
        checkIfLeftBracketIsMissed(expression, firstBracketIndex);
        while (lastBracketIndex < expression.length() && expression.charAt(lastBracketIndex) != ')') {
            if (expression.charAt(lastBracketIndex) == '(') {
                firstBracketIndex = lastBracketIndex;
            }
            lastBracketIndex++;
        }
        checkIfRightBracketIsMissed(expression, lastBracketIndex);
        return new int[]{firstBracketIndex, lastBracketIndex};
    }
}
