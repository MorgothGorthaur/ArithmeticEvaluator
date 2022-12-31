package com.example.myTestApp.service;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import org.springframework.stereotype.Component;

@Component
class StringExpressionHandlerImpl implements StringExpressionHandler{
    @Override
    public int[] getBracketIndexes(String expression) {
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

    @Override
    public double getOperand(String expression, int startIndex, int endIndex) {
        if (expression.contains("(")) throw new MissedRightBracketException();
        if (expression.contains(")")) throw new MissedLeftBracketException();
        if(expression.equals("")) return 0;
        try {
            return Double.parseDouble(expression.substring(startIndex, endIndex));
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
    public int getOperandEndIndex(String expression, int opIndex) {
        var lastIndex = opIndex + 1;
        while (lastIndex+1  < expression.length() && (expression.charAt(lastIndex + 1) == '.' || Character.isDigit(expression.charAt(lastIndex + 1)))) {
            lastIndex++;
        }
        return lastIndex+1;
    }

    @Override
    public int getOperandStartIndex(String expression, int opIndex) {
        var firstIndex = opIndex - 1;
        while (firstIndex > 0  && (expression.charAt(firstIndex - 1) == '.' || Character.isDigit(expression.charAt(firstIndex - 1)))) {
            firstIndex--;
        }
        return firstIndex > 0 && expression.charAt(firstIndex-1) == '-' ? firstIndex-1 : firstIndex;
    }

    @Override
    public boolean checkIfExpressionContainsBrackets(String exp) {
        return exp.contains("(") && exp.contains(")");
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
