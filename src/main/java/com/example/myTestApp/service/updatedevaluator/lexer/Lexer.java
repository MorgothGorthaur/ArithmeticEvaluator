package com.example.myTestApp.service.updatedevaluator.lexer;

import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.Lexeme;

import java.util.List;

public interface Lexer {
    List<Lexeme> toLexemeList (String receivedString);
}
