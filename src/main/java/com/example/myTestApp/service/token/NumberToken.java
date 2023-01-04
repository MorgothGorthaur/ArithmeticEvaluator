package com.example.myTestApp.service.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class NumberToken implements Token{
    private Double number;
    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
