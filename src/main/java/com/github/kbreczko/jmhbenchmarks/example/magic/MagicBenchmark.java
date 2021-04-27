package com.github.kbreczko.jmhbenchmarks.example.magic;

import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MagicBenchmark {

    @State(Scope.Benchmark)
    public static class SumPlan {
        public List<BigDecimal> numbers;
        public BigDecimal sum;

        @Setup(Level.Iteration)
        public void setUp() {
            this.numbers = DoubleStream.iterate(0.5, x -> x + 10)
                    .mapToObj(BigDecimal::new)
                    .limit(10_000)
                    .collect(Collectors.toList());
            this.sum = BigDecimal.valueOf(499_955_000);
        }
    }

    @Benchmark
    public BigDecimal doMagic(SumPlan state) {
        final Calculator calculator = new Calculator(state.sum);
        return calculator.sum(state.numbers);
    }
}
