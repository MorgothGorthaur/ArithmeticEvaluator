package com.example.myTestApp.controller;


import com.example.myTestApp.exception.BadOperandException;
import com.example.myTestApp.exception.ExpressionNotFound;
import com.example.myTestApp.model.Expression;
import com.example.myTestApp.repository.ExpressionRepository;
import com.example.myTestApp.service.ArithmeticExpressionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TestAppRestController {
    private final ExpressionRepository repository;
    private final ArithmeticExpressionEvaluator evaluator;

    @PostMapping
    public Expression getResult(@RequestBody Expression expression) {
        var res = evaluator.getEvaluation(expression.getArithmeticExpression());
        expression.setResult(res);
        repository.save(expression);
        return expression;

    }

    @PatchMapping
    public Expression updateExpression(@RequestBody Expression expression) {
        var exp = repository.findById(expression.getId())
                .orElseThrow(() -> new ExpressionNotFound(expression.getId()));
        exp.setArithmeticExpression(expression.getArithmeticExpression());
        exp.setResult(evaluator.getEvaluation(expression.getArithmeticExpression()));
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
