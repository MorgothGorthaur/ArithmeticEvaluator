package com.example.myTestApp.service.lexer;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.service.token.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class LexerImpl implements Lexer {
    @Override
    public List<Token> tokenize(String receivedString) {
        var expression = removeDoubleSpaces(receivedString);
        return getTokens(expression);
    }

    private List<Token> getTokens(String expression) {
        var tokens = new LinkedList<Token>();
        var iterator = 0;
        while (iterator + 1 < expression.length()) {
            switch (expression.charAt(iterator)) {
                case '(' -> tokens.add(new BracketToken(true));
                case ')' -> tokens.add(new BracketToken(false));
                case '*' -> tokens.add(new OperationToken(OperationType.MULTIPLICATION));
                case '/' -> tokens.add(new OperationToken(OperationType.DIVISION));
                case '+' -> {
                    if (expression.charAt(iterator - 1) == '*' || expression.charAt(iterator - 1) == '/') {
                        iterator = addOperand(expression, tokens, iterator);
                    } else {
                        tokens.add(new OperationToken(OperationType.ADDITION));
                    }
                }
                case '-' -> {
                    if (expression.charAt(iterator - 1) == '*' || expression.charAt(iterator - 1) == '/') {
                        iterator = addOperand(expression, tokens, iterator);
                    } else {
                        tokens.add(new OperationToken(OperationType.SUBTRACTION));
                    }
                }
                default -> iterator = addOperand(expression, tokens, iterator);

            }
            iterator++;
        }
        return tokens;
    }

    private int addOperand(String expression, LinkedList<Token> tokens, int iterator) {
        var index = getLastTokenIndex(expression, iterator + 1);
        var operand = index == iterator ? String.valueOf(expression.charAt(index)) : expression.substring(iterator, index);
        tokens.add(new NumberToken(Double.parseDouble(operand)));
        iterator = index - 1;
        return iterator;
    }

    private Integer getLastTokenIndex(String expression, Integer iterator) {
        var charArr = expression.toCharArray();
        var lastTokenIndex = iterator;
        while (lastTokenIndex + 1 <= charArr.length && (Character.isDigit(charArr[lastTokenIndex]) || charArr[lastTokenIndex] == '.')) {
            lastTokenIndex++;
        }
        if (lastTokenIndex == 0 && !Character.isDigit(charArr[lastTokenIndex])) {
            throw new BadOperandException();
        }
        return lastTokenIndex;
    }

    private String removeDoubleSpaces(String string) {
        if (string.length() > 0 && string.charAt(0) == '+') string = string.substring(1);
        return string.strip().replaceAll("\\ ", "").replaceAll("--", "+").replaceAll("\\++", "+");
    }
}