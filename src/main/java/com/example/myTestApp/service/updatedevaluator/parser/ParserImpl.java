package com.example.myTestApp.service.updatedevaluator.parser;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedLeftBracketException;
import com.example.myTestApp.exception.MissedRightBracketException;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.Lexeme;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.OperationType;

import java.util.LinkedList;
import java.util.List;

public class ParserImpl implements Parser{
    @Override
    public Integer getResult(List<Lexeme> lexemes) {
        checkIfBracketsDoesntMissed(lexemes);
        checkIfLexemesContainsBadOperatorSequence(lexemes);
        return null;
    }

    @Override
    public Integer getNumOfOperands(List<Lexeme> lexemes) {
        return lexemes.stream().filter(lexeme -> lexeme.operationType().equals(OperationType.OPERAND)).toList().size();
    }

    private void checkIfBracketsDoesntMissed(List<Lexeme> lexemes) {
        var amountOfLeftBrackets = lexemes.stream().filter(lexeme -> lexeme.operationType().equals(OperationType.LEFT_BRACKET)).toList().size();
        var amountOfRightBrackets = lexemes.stream().filter(lexeme -> lexeme.operationType().equals(OperationType.RIGHT_BRACKET)).toList().size();
        if(amountOfLeftBrackets > amountOfRightBrackets) throw new MissedRightBracketException();
        if(amountOfLeftBrackets < amountOfRightBrackets) throw new MissedLeftBracketException();
    }
    private void checkIfLexemesContainsBadOperatorSequence(List<Lexeme> lexemes) {
        var it = lexemes.iterator();
        var lex = it.next();
        var next = it.next();
        while (it.hasNext()) {
            if(next.operationType().equals(OperationType.MULTIPLICATION) || next.operationType().equals(OperationType.DIVISION)) {
                if(lex.operationType().equals(OperationType.ADDITION) || lex.operationType().equals(OperationType.SUBTRACTION)) {
                    throw new BadOperandException();
                }
            }
            lex = next;
            next = it.next();
        }
    }





}
