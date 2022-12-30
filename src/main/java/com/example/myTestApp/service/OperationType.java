package com.example.myTestApp.service;

import com.example.myTestApp.exception.DivisionByZeroException;

enum OperationType {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION;

    double doOperation(double firstOperand, double secondOperand) {
        switch (ordinal()) {
            case 0 -> {return firstOperand + secondOperand;}
            case 1 -> {return firstOperand - secondOperand;}
            case 2 -> {return  Math.round(1000 * firstOperand * secondOperand) / 1000.0;}
            case 3 -> {
                if(secondOperand != 0) {
                    return Math.round(1000 * (firstOperand / secondOperand)) / 1000.0;
                } else throw new DivisionByZeroException();
            }
            default -> throw new RuntimeException("unknown operation type!");
        }
    }
}