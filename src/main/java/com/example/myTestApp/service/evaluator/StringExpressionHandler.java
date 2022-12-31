package com.example.myTestApp.service.evaluator;


public interface StringExpressionHandler {
    String[] splitByBrackets(String expression);

    double getOperand(String operation, int startIndex, int endIndex);

    String removeDoubleSymbolsAndSpaces(String string);

    int getSecondOperandEndIndex(String operation, int opIndex);

    int getFirstOperandStartIndex(String operation, int opIndex);

    boolean checkIfExpressionContainsBrackets(String exp);
    void checkIfExpressionDoesntMissedBrackets(String expression);
}
