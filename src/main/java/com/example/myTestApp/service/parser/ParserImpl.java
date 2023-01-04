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
                    getAdditionOrSubtractionResult(tokens,leftOperand,(OperationToken)  operation);
        } else throw new OperationExpectedException();
    }

    private boolean expressionIsEnded(LinkedList<Token> tokens) {
        return tokens.isEmpty() || (tokens.getFirst() instanceof BracketToken && !((BracketToken) tokens.getFirst()).isOpen());
    }

    private Double getAdditionOrSubtractionResult(LinkedList<Token> tokens, Double leftOperand, OperationToken operation){
        var rightToken = tokens.removeFirst();
        rightToken = checkIfRightTokenIsBracket(tokens, rightToken);
        if(rightToken instanceof NumberToken) {
            if(operation.getOperationType().equals(OperationType.SUBTRACTION)){
                operation.setOperationType(OperationType.ADDITION);
                ((NumberToken) rightToken).setNumber(-1 * ((NumberToken) rightToken).getNumber());
            }
        } else throw new BadOperandException();
        tokens.addFirst(rightToken);
        return operation.doOperation(leftOperand, evaluateExpression(tokens));
    }
    private Double getMultiplyOrDivideResult(LinkedList<Token> tokens, Double leftOperand, OperationToken operation) {
        var rightToken = tokens.removeFirst();
        rightToken = checkIfRightTokenIsBracket(tokens, rightToken);
        if(rightToken instanceof NumberToken){
            var res = operation.doOperation(leftOperand, ((NumberToken) rightToken).getNumber());
            tokens.addFirst(new NumberToken(res));
        } else throw new BadOperandException();
        return evaluateExpression(tokens);
    }

    private Token checkIfRightTokenIsBracket(LinkedList<Token> tokens, Token rightToken) {
        if (rightToken instanceof BracketToken && ((BracketToken) rightToken).isOpen()) {
             getResultFromBrackets(tokens);
             rightToken = tokens.removeFirst();
        }
        return rightToken;
    }

    private void getResultFromBrackets(LinkedList<Token> tokens) {
        if(tokens.getFirst() instanceof BracketToken && !((BracketToken) tokens.getFirst()).isOpen()) throw new EmptyExpressionException();
        var result =  evaluateExpression(tokens);
        tokens.removeFirst();
        tokens.addFirst(new NumberToken(result));
    }

}
