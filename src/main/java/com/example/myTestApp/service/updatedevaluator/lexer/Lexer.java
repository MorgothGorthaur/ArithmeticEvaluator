package com.example.myTestApp.service.updatedevaluator.lexer;

import com.example.myTestApp.service.updatedevaluator.token.Token;

import java.util.List;

public interface Lexer {
    List<Token> tokenize (String receivedString);
}
