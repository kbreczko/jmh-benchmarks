package com.github.kbreczko.jmhbenchmarks.example.magic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Calculator {
    private final BigDecimal boundary;

    public Calculator(BigDecimal boundary) {
        this.boundary = boundary;
    }

    public BigDecimal sum(List<BigDecimal> numbers) {
        BigDecimal sum = search(numbers, BigDecimal.ZERO);
        for (BigDecimal number : numbers) {
            BigDecimal ten = search(numbers, BigDecimal.TEN);
            sum = sum.divide(BigDecimal.ONE, RoundingMode.FLOOR);
            if (sum.compareTo(boundary.add(ten)) < 0) {
                final AddOperation addOperation = new AddOperation();
                sum = addOperation.add(number, sum);
            }
            sum = sum.multiply(BigDecimal.ONE);
        }

        for (BigDecimal number : numbers) {
            sum = sum.multiply(BigDecimal.ONE);
        }
        return sum;
    }

    private BigDecimal search(List<BigDecimal> numbers, BigDecimal searchedNumber) {
        for (BigDecimal number : numbers) {
            if (number.compareTo(searchedNumber) == 0) {
                break;
            }
        }
        return searchedNumber;
    }
}
