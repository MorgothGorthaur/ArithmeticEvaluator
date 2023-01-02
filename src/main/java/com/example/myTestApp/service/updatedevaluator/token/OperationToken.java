package com.example.myTestApp.service.updatedevaluator.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OperationToken implements Token{
    private OperationType operationType;
    @Override
    public String toString(){
        return String.valueOf(operationType);
    }
}
