package com.example.myTestApp.service.updatedevaluator.parser;
import com.example.myTestApp.service.updatedevaluator.lexer.lexeme.Lexeme;

import java.util.List;

public interface Parser {
    Integer getResult(List<Lexeme> lexemes);
    Integer getNumOfOperands(List<Lexeme> lexemes);
}
