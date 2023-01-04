package com.example.myTestApp.repository;

import com.example.myTestApp.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
    List<Expression> getExpressionsByResult(Double result);

    @Query(value = "select exp from Expression exp where exp.result < ?1")
    List<Expression> getExpressionsWithLowerResults(Double result);

    @Query(value = "select exp from Expression exp where exp.result > ?1")
    List<Expression> getExpressionsWithHigherResults(Double result);
}
