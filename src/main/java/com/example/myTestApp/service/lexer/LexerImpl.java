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
        var expression = removeDoubleSpacesAndOperators(receivedString);
        return getTokens(expression);
    }

    private List<Token> getTokens(String expression) {
        var tokens = new LinkedList<Token>();
        var iterator = 0;
        while (iterator < expression.length()) {
            switch (expression.charAt(iterator)) {
                case '(' -> tokens.add(new BracketToken(true));
                case ')' -> tokens.add(new BracketToken(false));
                case '*' -> tokens.add(new OperationToken(OperationType.MULTIPLICATION));
                case '/' -> tokens.add(new OperationToken(OperationType.DIVISION));
                case '+' -> {
                    if (ifFirstOrContainsAnotherOperatorBehind(expression, iterator)) {
                        iterator = addOperand(expression, tokens, iterator);
                    } else {
                        tokens.add(new OperationToken(OperationType.ADDITION));
                    }
                }
                case '-' -> {
                    if (ifFirstOrContainsAnotherOperatorBehind(expression, iterator)) {
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

    private boolean ifFirstOrContainsAnotherOperatorBehind(String expression, int iterator) {
        return iterator == 0 || (expression.charAt(iterator - 1) == '*' || expression.charAt(iterator - 1) == '/');
    }

    private int addOperand(String expression, LinkedList<Token> tokens, int iterator) {
        try {
            var index = getLastTokenIndex(expression, iterator);
            var operand = index == iterator ? String.valueOf(expression.charAt(index)) : expression.substring(iterator, index);
            if(operand.charAt(0) =='.' || (operand.length() > 1 && operand.charAt(1) == '.' && (operand.charAt(0) == '+' || operand.charAt(0) =='-'))) throw new BadOperandException();
            tokens.add(new NumberToken(Double.parseDouble(operand)));
            iterator = index - 1;
            return iterator;
        } catch (RuntimeException ex) {
            throw new BadOperandException();
        }

    }

    private Integer getLastTokenIndex(String expression, Integer iterator) {
        var charArr = expression.toCharArray();
        if(!Character.isDigit(charArr[iterator])) iterator ++;
        var lastTokenIndex = iterator;
        while (lastTokenIndex  < charArr.length && (Character.isDigit(charArr[lastTokenIndex]) || charArr[lastTokenIndex] == '.')) {
            lastTokenIndex++;
        }
        return lastTokenIndex;
    }

    private String removeDoubleSpacesAndOperators(String string) {
        return string.strip().replaceAll("\\ ", "");
    }
}
