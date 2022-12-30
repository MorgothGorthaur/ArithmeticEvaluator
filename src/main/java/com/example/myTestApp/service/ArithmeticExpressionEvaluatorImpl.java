package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.DivisionByZeroException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import org.springframework.stereotype.Service;

@Service
class ArithmeticExpressionEvaluatorImpl implements ArithmeticExpressionEvaluator {
    @Override
    public double getEvaluation(String arithmeticExpression) {
        if (checkIfExpressionContainsBrackets(arithmeticExpression)) {
            arithmeticExpression = simplifyExpression(check(arithmeticExpression));
        }
        var res = evaluation(check(arithmeticExpression));
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
        if (bracketIndexes[0] - 1 != 0 && Character.isDigit(expression.charAt(bracketIndexes[0] - 1))) {
            expression = expression.substring(0, bracketIndexes[0]) + '*' + expression.substring(bracketIndexes[0]);
            bracketIndexes[0] += 1;
            bracketIndexes[1] += 1;
        }
        var firstExpression = expression.substring(0, bracketIndexes[0]);
        var lastExpression = expression.substring(bracketIndexes[1] + 1);
        var subExpression = checkIfExpressionContainsBrackets(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1]))
                ? simplifyExpression(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1]))
                : String.valueOf(evaluation(expression.substring(bracketIndexes[0] + 1, bracketIndexes[1])));
        var res = firstExpression + subExpression + lastExpression;
        return checkIfExpressionContainsBrackets(res) ? simplifyExpression(check(res)) : check(res);
    }

    private int[] getBracketsIndexes(String expression) {
        var firstBracketIndex = expression.indexOf("(");
        var lastBracketIndex = firstBracketIndex + 1;
        checkLeftBracketIsMissed(expression, firstBracketIndex);
        while (lastBracketIndex < expression.length() && expression.charAt(lastBracketIndex) != ')') {
            if (expression.charAt(lastBracketIndex) == '(') {
                firstBracketIndex = lastBracketIndex;
            }
            lastBracketIndex++;
        }
        checkIfRightBracketIsMissed(expression, lastBracketIndex);
        return new int[]{firstBracketIndex, lastBracketIndex};
    }

    private String evaluation(String string) {
        var opIndexes = new int[]{string.indexOf('*'), string.indexOf('/'), string.indexOf('+'), string.indexOf('-')};
        if ((opIndexes[0] <= opIndexes[1] || opIndexes[1] == -1) && opIndexes[0] != -1) {
            string = evaluation(doOperation(OperationType.MULTIPLICATION, opIndexes[0], string));
        } else if (opIndexes[1] != -1) {
            string = evaluation(doOperation(OperationType.DIVISION, opIndexes[1], string));
        } else if (opIndexes[2] != -1) {
            string = evaluation(doOperation(OperationType.ADDITION, opIndexes[2], string));
        } else if (opIndexes[3] > 0) {
            string = evaluation(doOperation(OperationType.SUBTRACTION, opIndexes[3], string));
        }
        return string;
    }

    private String doOperation(OperationType operationType, int opIndex, String expression) {
        var indexes = getOperandValuesIndexes(expression, opIndex);
        var firstOperand = indexes[0] == -1 ? 0 : getOperand(expression, indexes[0], opIndex);
        var lastOperand = getOperand(expression, opIndex + 1, indexes[1]);
        var res = indexes[0] == -1 ? "" : expression.substring(0, indexes[0]);
        res += "+";
        res += operationType.doOperation(firstOperand, lastOperand);
        res += expression.substring(indexes[1]);
        return check(res);
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

    public int getFirstOperandIndex(String expression, int opIndex) {
        var firstIndex = opIndex - 1;
        if (firstIndex == -1 && (expression.charAt(opIndex) == '*' || expression.charAt(opIndex) == '/')) {
            throw new BadOperandException();
        }
        while (firstIndex - 1 > -1 && (expression.charAt(firstIndex - 1) == '.' || Character.isDigit(expression.charAt(firstIndex - 1)))) {
            firstIndex--;
        }
        if (firstIndex - 1 > -1 && expression.charAt(firstIndex - 1) == '-') {
            firstIndex--;
        }
        return firstIndex;
    }

    private double getOperand(String expression, int firstIndex, int lastIndex) {
        if (expression.contains("(")) throw new MissedRightBracketException();
        if (expression.contains(")")) throw new MissedLeftBracketException();
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

    private void checkLeftBracketIsMissed(String expression, int firstBracketIndex) {
        if (expression.indexOf(")") < firstBracketIndex) {
            throw new MissedLeftBracketException();
        }
    }

}
