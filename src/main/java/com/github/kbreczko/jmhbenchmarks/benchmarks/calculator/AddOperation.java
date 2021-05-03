package com.github.kbreczko.jmhbenchmarks.benchmarks.calculator;

import java.math.BigDecimal;

public class AddOperation {

    public BigDecimal add(BigDecimal number1, BigDecimal number2) {
        if(number1 == null){
            throw new IllegalArgumentException();
        }

        if(number2 == null){
            throw new IllegalArgumentException();
        }

        return number1.add(number2);
    }
}
