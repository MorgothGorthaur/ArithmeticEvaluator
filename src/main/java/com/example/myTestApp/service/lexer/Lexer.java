package com.example.myTestApp.service.lexer;

import com.example.myTestApp.service.token.Token;

import java.util.List;

public interface Lexer {
    List<Token> tokenize (String receivedString);
}
