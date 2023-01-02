package com.example.myTestApp.service.updatedevaluator.lexer;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.Lexeme;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.OperationType;

import java.util.LinkedList;
import java.util.List;

public class LexerImpl implements Lexer {
    @Override
    public List<Lexeme> toLexemeList(String receivedString) {
        var expression = removeDoubleOperatorsAndSpaces(receivedString);
        return getLexeme(expression);
    }

    private List<Lexeme> getLexeme(String expression) {
        var lexemes = new LinkedList<Lexeme>();
        if (expression.length() != 0) {
            switch (expression.charAt(0)) {
                case '(' -> lexemes.add(new Lexeme(OperationType.LEFT_BRACKET));
                case ')' -> lexemes.add(new Lexeme(OperationType.RIGHT_BRACKET));
                case '*' -> lexemes.add(new Lexeme(OperationType.MULTIPLICATION));
                case '/' -> lexemes.add(new Lexeme(OperationType.DIVISION));
                case '+' -> lexemes.add(new Lexeme(OperationType.ADDITION));
                case '-' -> lexemes.add(new Lexeme(OperationType.SUBTRACTION));
                default -> {
                    var index = getLastLexemeIndex(expression);
                    var operand = index == 0 ? String.valueOf(expression.charAt(index)) : expression.substring(0, index);
                    lexemes.add(new Lexeme(Double.parseDouble(operand)));
                    expression = index == 0 ? expression : expression.substring(index-1);
                }
            }
            lexemes.addAll(getLexeme(expression.substring(1)));
        }
        return lexemes;
    }

    private Integer getLastLexemeIndex(String expression) {
        var charArr = expression.toCharArray();
        var lastLexemeIndex = 0;
        while (lastLexemeIndex + 1 < charArr.length && (Character.isDigit(charArr[lastLexemeIndex]) || charArr[lastLexemeIndex] == '.')) {
            lastLexemeIndex++;
        }
        if(lastLexemeIndex == 0 && !Character.isDigit(charArr[lastLexemeIndex])) {
            throw new BadOperandException();
        }
        return lastLexemeIndex;
    }

    private String removeDoubleOperatorsAndSpaces(String string) {
        if (string.length() > 0 && string.charAt(0) == '+') string = string.substring(1);
        return string.strip().replaceAll("\\ ", "");
    }
}
