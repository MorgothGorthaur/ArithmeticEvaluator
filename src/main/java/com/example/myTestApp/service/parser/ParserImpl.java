package com.example.myTestApp.service.parser;

import com.example.myTestApp.exception.*;
import com.example.myTestApp.service.token.*;
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

    private void checkIfBracketsDoesntMissed(List<Token> tokens) {
        var lefBracketsNum = tokens.stream().filter(token -> token instanceof BracketToken && ((BracketToken) token).isOpen()).toList().size();
        var rightBracketsNum = tokens.stream().filter(token -> token instanceof BracketToken && !((BracketToken) token).isOpen()).toList().size();
        if (lefBracketsNum > rightBracketsNum) throw new MissedRightBracketException();
        if (lefBracketsNum < rightBracketsNum) throw new MissedLeftBracketException();

    }

    private Double evaluateExpression(LinkedList<Token> tokens) {
        if (tokens.isEmpty()) throw new EmptyExpressionException();
        var nextToken = tokens.removeFirst();
        if (nextToken instanceof NumberToken nextNumberToken) {
            return doArithmeticOperation(tokens, nextNumberToken);
        } else if (nextToken instanceof BracketToken nextBracketToken && nextBracketToken.isOpen()) {
            getResultFromBrackets(tokens);
            return evaluateExpression(tokens);
        } else throw new OperandExpectedException();
    }

    private Double doArithmeticOperation(LinkedList<Token> tokens, NumberToken nextToken) {
        var leftOperand = nextToken.getNumber();
        if (expressionIsEnded(tokens)) return leftOperand;
        var operation = tokens.removeFirst();
        if (operation instanceof OperationToken operationToken) {
            var operationType = operationToken.getOperationType();
            if (tokens.isEmpty()) throw new OperandExpectedException();
            var rightToken = tokens.removeFirst();
            rightToken = checkIfRightTokenIsBracket(tokens, rightToken);
            return operationType.equals(OperationType.MULTIPLICATION) || operationType.equals(OperationType.DIVISION) ?
                    getMultiplyOrDivideResult(tokens, leftOperand, (OperationToken) operation, rightToken) :
                    getAdditionOrSubtractionResult(tokens, leftOperand, (OperationToken) operation, rightToken);
        } else throw new OperationExpectedException();
    }

    private boolean expressionIsEnded(LinkedList<Token> tokens) {
        return tokens.isEmpty() || (tokens.getFirst() instanceof BracketToken bracketToken && !bracketToken.isOpen());
    }

    private Double getAdditionOrSubtractionResult(LinkedList<Token> tokens, Double leftOperand, OperationToken operation, Token rightToken) {
        if (rightToken instanceof NumberToken rightNumberToken) {
            if (operation.getOperationType().equals(OperationType.SUBTRACTION)) {
                operation.setOperationType(OperationType.ADDITION);
                rightNumberToken.setNumber(-1 * rightNumberToken.getNumber());
            }
        } else throw new OperandExpectedException();
        tokens.addFirst(rightToken);
        return operation.doOperation(leftOperand, evaluateExpression(tokens));
    }

    private Double getMultiplyOrDivideResult(LinkedList<Token> tokens, Double leftOperand, OperationToken operation, Token rightToken) {
        if (rightToken instanceof NumberToken rightNumberToken) {
            var res = operation.doOperation(leftOperand, rightNumberToken.getNumber());
            tokens.addFirst(new NumberToken(res));
        } else throw new OperandExpectedException();
        return evaluateExpression(tokens);
    }

    private Token checkIfRightTokenIsBracket(LinkedList<Token> tokens, Token rightToken) {
        if (rightToken instanceof BracketToken rightBracketToken && rightBracketToken.isOpen()) {
            getResultFromBrackets(tokens);
            rightToken = tokens.removeFirst();
        }
        return rightToken;
    }

    private void getResultFromBrackets(LinkedList<Token> tokens) {
        if (tokens.getFirst() instanceof BracketToken bracketToken && !bracketToken.isOpen())
            throw new EmptyExpressionException();
        var result = evaluateExpression(tokens);
        tokens.removeFirst();
        tokens.addFirst(new NumberToken(result));
    }

}
