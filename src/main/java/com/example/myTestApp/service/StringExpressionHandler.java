package com.example.myTestApp.service;

import java.util.List;

public interface StringExpressionHandler {
    String [] splitByBrackets(String expression);
    double getOperand(String operation, int startIndex, int endIndex);
    String removeDoubleSymbolsAndSpaces(String string);
    int getOperandEndIndex(String operation, int opIndex);
    int getOperandStartIndex(String operation, int opIndex);
    boolean checkIfExpressionContainsBrackets(String exp);
}
