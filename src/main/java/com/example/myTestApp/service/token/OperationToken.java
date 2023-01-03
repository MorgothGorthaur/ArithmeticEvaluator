package com.example.myTestApp.service.token;

import com.example.myTestApp.exception.DivisionByZeroException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter @Setter
public class OperationToken implements Token{
    private OperationType operationType;
    @Override
    public String toString(){
        return String.valueOf(operationType);
    }
    public double doOperation(double firstOperand, double secondOperand) {
        switch (operationType.ordinal()) {
            case 0 -> {return firstOperand + secondOperand;}
            case 1 -> {return firstOperand - secondOperand;}
            case 2 -> {return  Math.round(1000 * firstOperand * secondOperand) / 1000.0;}
            case 3 -> {
                if(secondOperand != 0) {
                    return Math.round(1000 * (firstOperand / secondOperand)) / 1000.0;
                } else throw new DivisionByZeroException();
            }
            default -> throw new RuntimeException("unknown operation type!");
        }
    }
}
