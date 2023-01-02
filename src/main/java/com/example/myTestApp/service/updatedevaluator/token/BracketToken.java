package com.example.myTestApp.service.updatedevaluator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BracketToken implements Token{
    private boolean isOpen;
    @Override
    public String toString() {
        return isOpen ? "(" : ")";
    }
}
