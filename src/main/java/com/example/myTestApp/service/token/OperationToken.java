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
        var res = 0.0;
        switch (operationType.ordinal()) {
            case 0 -> res =  firstOperand + secondOperand;
            case 1 -> res = firstOperand - secondOperand;
            case 2 -> res =  firstOperand * secondOperand;
            case 3 -> {
                if(secondOperand != 0) {
                    res = firstOperand / secondOperand;
                } else throw new DivisionByZeroException();
            }
            default -> throw new RuntimeException("unknown operation type!");
        }
        return Math.round(1000 * res) / 1000.0;
    }
}
