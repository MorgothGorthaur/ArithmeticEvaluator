package com.example.myTestApp.service.updatedevaluator.parser;

import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.MissedRightBracketException;
import com.example.myTestApp.service.updatedevaluator.token.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private final Parser parser = new ParserImpl();


    @Test
    void evaluate() {
        //2+3*(2*(6+3))
        var tokens = new LinkedList<Token>();
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(3.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(2.0));
        tokens.add(new OperationToken(OperationType.MULTIPLICATION));
        tokens.add(new BracketToken(true));
        tokens.add(new NumberToken(6.0));
        tokens.add(new OperationToken(OperationType.ADDITION));
        tokens.add(new NumberToken(-21013.0));
        tokens.add(new BracketToken(false));
        tokens.add(new BracketToken(false));
        var res = parser.evaluate(tokens);
        System.out.println(res);
    }

    @Test
    void getNumOfOperands() {
    }
}