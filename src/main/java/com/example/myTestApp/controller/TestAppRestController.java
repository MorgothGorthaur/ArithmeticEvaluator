package com.example.myTestApp.controller;


import com.example.myTestApp.exception.ExpressionNotFoundException;
import com.example.myTestApp.model.Expression;
import com.example.myTestApp.repository.ExpressionRepository;
import com.example.myTestApp.service.evaluator.ArithmeticExpressionEvaluator;
import com.example.myTestApp.service.counter.NumOfDoublesCounter;
import com.example.myTestApp.service.updatedevaluator.lexer.Lexer;
import com.example.myTestApp.service.updatedevaluator.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@CrossOrigin("*")
public class TestAppRestController {
    private final ExpressionRepository repository;
    private final Lexer lexer;
    private final Parser parser;

    @PostMapping
    public Expression getResult(@RequestBody Expression expression) {
        expression.setId(null);
        var tokens = lexer.tokenize(expression.getArithmeticExpression());
        expression.setNumOfDoubles(parser.getNumOfOperands(tokens));
        expression.setResult(parser.evaluate(tokens));
        repository.save(expression);
        return expression;

    }

    @PatchMapping
    public Expression updateExpression(@RequestBody Expression expression) {
        var exp = repository.findById(expression.getId()).orElseThrow(() -> new ExpressionNotFoundException(expression.getId()));
        exp.setArithmeticExpression(expression.getArithmeticExpression());
        var tokens = lexer.tokenize(expression.getArithmeticExpression());
        exp.setNumOfDoubles(parser.getNumOfOperands(tokens));
        exp.setResult(parser.evaluate(tokens));
        return repository.save(exp);

    }

    @GetMapping("/all")
    public List<Expression> getExpressions() {
        return repository.findAll();
    }

    @GetMapping("/all/{res}")
    public List<Expression> getExpressionsByResult(@PathVariable Double res) {
        return repository.getExpressionsByResult(res);
    }

    @GetMapping("/all/lower/{res}")
    public List<Expression> getExpressionsWithLowerResults(@PathVariable Double res) {
        return repository.getExpressionsWithLowerResults(res);
    }

    @GetMapping("/all/upper/{res}")
    public List<Expression> getExpressionsWithUpperResults(@PathVariable Double res) {
        return repository.getExpressionsWithUpperResults(res);
    }

}
