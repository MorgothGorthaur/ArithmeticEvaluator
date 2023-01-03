package com.example.myTestApp.service.updatedevaluator.parser;

import com.example.myTestApp.exception.*;
import com.example.myTestApp.service.updatedevaluator.token.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class ParserImpl implements Parser {
    @Override
    public Double evaluate(List<Token> tokens) {
        checkIfBracketsDoesntMissed(tokens);
        return evaluateExpression(new LinkedList<>(tokens));
    }

    @Override
    public Integer getNumOfOperands(List<Token> lexemes) {
        return lexemes.stream().filter(token -> token instanceof NumberToken).toList().size();
    }

    private void checkIfBracketsDoesntMissed(List<Token>tokens){
        var lefBracketsNum = tokens.stream().filter(token -> token instanceof BracketToken && ((BracketToken) token).isOpen()).toList().size();
        var rightBracketsNum = tokens.stream().filter(token -> token instanceof BracketToken && !((BracketToken) token).isOpen()).toList().size();
        if(lefBracketsNum > rightBracketsNum) throw new MissedRightBracketException();
        if(lefBracketsNum < rightBracketsNum) throw new MissedLeftBracketException();

    }

    private Double evaluateExpression(LinkedList<Token> tokens) {
        if (tokens.isEmpty()) throw new EmptyExpressionException();
        var nextToken = tokens.removeFirst();
        if (nextToken instanceof NumberToken) {
            return doArithmeticOperation(tokens, (NumberToken) nextToken);
        } else if (nextToken instanceof BracketToken && ((BracketToken) nextToken).isOpen()) {
            getResultFromBrackets(tokens);
            return evaluateExpression(tokens);
        } else throw new BadOperandException();
    }

    private Double doArithmeticOperation(LinkedList<Token> tokens, NumberToken nextToken) {
        var leftOperand = nextToken.getNumber();
        if (expressionIsEnded(tokens)) return leftOperand;
        var operation = tokens.removeFirst();
        if (operation instanceof OperationToken) {
            var operationType = ((OperationToken) operation).getOperationType();
            return operationType.equals(OperationType.MULTIPLICATION) || operationType.equals(OperationType.DIVISION) ?
                    getMultiplyOrDivideResult(tokens, leftOperand, (OperationToken) operation) :
                    ((OperationToken) operation).doOperation(leftOperand, evaluateExpression(tokens));
        } else throw new OperationExpectedException();
    }

    private boolean expressionIsEnded(LinkedList<Token> tokens) {
        return tokens.isEmpty() || (tokens.getFirst() instanceof BracketToken && !((BracketToken) tokens.getFirst()).isOpen());
    }

    private Double getMultiplyOrDivideResult(LinkedList<Token> tokens, Double leftOperand, OperationToken operation) {
        var rightToken = tokens.removeFirst();
        var res = 0.0;
        if (rightToken instanceof BracketToken && ((BracketToken) rightToken).isOpen()) {
             getResultFromBrackets(tokens);
             rightToken = tokens.removeFirst();
        } else throw new BadOperandException();
        if(rightToken instanceof NumberToken){
            res = operation.doOperation(leftOperand, ((NumberToken) rightToken).getNumber());
        } else throw new BadOperandException();
        tokens.addFirst(new NumberToken(res));
        return evaluateExpression(tokens);
    }

    private void getResultFromBrackets(LinkedList<Token> tokens) {
        if(tokens.getFirst() instanceof BracketToken && !((BracketToken) tokens.getFirst()).isOpen()) throw new EmptyExpressionException();
        var result =  evaluateExpression(tokens);
        tokens.removeFirst();
        tokens.addFirst(new NumberToken(result));
    }

}
