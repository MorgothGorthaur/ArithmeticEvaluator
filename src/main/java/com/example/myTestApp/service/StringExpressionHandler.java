package com.example.myTestApp.service;

public interface StringExpressionHandler {
    int[] getBracketIndexes(String expression);

    double getOperand(String expression, int startIndex, int endIndex);
    String removeDoubleSymbolsAndSpaces(String string);
    int getOperandEndIndex(String expression, int opIndex);
    int getOperandStartIndex(String expression, int opIndex);
    boolean checkIfExpressionContainsBrackets(String exp);
}
