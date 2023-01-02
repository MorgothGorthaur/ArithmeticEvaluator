package com.example.myTestApp.service.updatedevaluator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NumberToken implements Token{
    private Double number;
    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
