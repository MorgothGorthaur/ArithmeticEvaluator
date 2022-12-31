package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
class StringExpressionHandlerImpl implements StringExpressionHandler{



    @Override
    public String[] splitByBrackets(String expression) {
        var bracketIndexes = getBracketIndexes(expression);
        if (bracketIndexes[0] - 1 >= 0 && Character.isDigit(expression.charAt(bracketIndexes[0] - 1))) {
            expression = expression.substring(0, bracketIndexes[0]) + '*' + expression.substring(bracketIndexes[0]);
            bracketIndexes[0] += 1;
            bracketIndexes[1] += 1;
        }
        return new String[]{expression.substring(0, bracketIndexes[0]), expression.substring(bracketIndexes[0] + 1, bracketIndexes[1]), expression.substring(bracketIndexes[1] + 1)};

    }

    @Override
    public double getOperand(String operation, int startIndex, int endIndex) {
        if (operation.contains("(")) throw new MissedRightBracketException();
        if (operation.contains(")")) throw new MissedLeftBracketException();
        if(operation.equals("")) return 0;
        try {
            return Double.parseDouble(operation.substring(startIndex, endIndex));
        } catch (RuntimeException ex) {
            throw new BadOperandException();
        }
    }

    @Override
    public String removeDoubleSymbolsAndSpaces(String string) {
        if (string.length() >0 && string.charAt(0) == '+') string = string.substring(1);
        return string.strip().replaceAll("\\ ", "")
                .replaceAll("--", "+")
                .replaceAll("\\+-", "-")
                .replaceAll("-\\+", "-")
                .replaceAll("\\++", "+");
    }

    @Override
    public int getOperandEndIndex(String operation, int opIndex) {
        var lastIndex = opIndex + 1;
        while (lastIndex+1  < operation.length() && (operation.charAt(lastIndex + 1) == '.' || Character.isDigit(operation.charAt(lastIndex + 1)))) {
            lastIndex++;
        }
        return lastIndex+1;
    }

    @Override
    public int getOperandStartIndex(String operation, int opIndex) {
        var firstIndex = opIndex - 1;
        while (firstIndex > 0  && (operation.charAt(firstIndex - 1) == '.' || Character.isDigit(operation.charAt(firstIndex - 1)))) {
            firstIndex--;
        }
        return firstIndex > 0 && operation.charAt(firstIndex-1) == '-' ? firstIndex-1 : firstIndex;
    }

    @Override
    public boolean checkIfExpressionContainsBrackets(String exp) {
        return exp.contains("(") && exp.contains(")");
    }
    private int[] getBracketIndexes(String expression) {
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
}
