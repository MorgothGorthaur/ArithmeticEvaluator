package com.example.myTestApp.service.parser;
import com.example.myTestApp.service.token.Token;

import java.util.List;

public interface Parser {
    Double evaluate(List<Token> lexemes);
    Integer getNumOfOperands(List<Token> lexemes);
}
