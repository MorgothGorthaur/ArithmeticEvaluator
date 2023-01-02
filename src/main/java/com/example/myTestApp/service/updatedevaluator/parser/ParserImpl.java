package com.example.myTestApp.service.updatedevaluator.parser;

import com.example.myTestApp.service.updatedevaluator.token.NumberToken;
import com.example.myTestApp.service.updatedevaluator.token.Token;

import java.util.LinkedList;
import java.util.List;

public class ParserImpl implements Parser{
    @Override
    public Integer evaluate(List<Token> lexemes) {
        return null;
    }

    @Override
    public Integer getNumOfOperands(List<Token> lexemes) {
        return lexemes.stream().filter(token -> token instanceof NumberToken).toList().size();
    }



    private Double evaluateLexemes(LinkedList<Token> lexemes) {
        if(lexemes.isEmpty()) throw new RuntimeException("empty!");
        var nextLexeme = lexemes.removeFirst();
        return null;

    }




}
