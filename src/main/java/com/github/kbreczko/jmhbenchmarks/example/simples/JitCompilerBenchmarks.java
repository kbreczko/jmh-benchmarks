package com.github.kbreczko.jmhbenchmarks.example.simples;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JitCompilerBenchmarks {

    @State(Scope.Benchmark)
    public static class SimplePlan {
        public List<Integer> numbers;
        public int sum;
        public int iterations;

        @Setup(Level.Iteration)
        public void setUp() {
            this.numbers = IntStream.range(0, 100_000)
                    .boxed()
                    .collect(Collectors.toList());
            this.sum = Integer.MAX_VALUE;
            this.iterations = 1_000_000;
        }
    }

    @Benchmark
    public Double extractOperationOutOfLoop(SimplePlan state) {
        double sum = 0;
        double result = 0;
        double x = 100;
        for (int i = 1; i < state.iterations; i++) {
            double v1 = Math.log((i + 1) * x) + Math.log(x);
            double v2 = 2 + Math.log((i + 1) * x) * Math.log(x);
            sum += v1 / v2;
            if (i == state.iterations - 1) {
                result = Math.log(sum);
            }
        }
        return result;
    }

    @Benchmark
    public String redundantCode(SimplePlan state) {
        final String firstChar = "1";
        final StringBuilder firstChars = new StringBuilder();
        for (Integer number : state.numbers) {
            final Integer searched = search(state.numbers, number);
            Supplier<String> stringSupplier = () -> "x";
            final String value = getValueFromSupplier(stringSupplier);
            final char c = value.charAt(0);
            firstChars.append(c);
        }
        return firstChars.toString();
    }

    private Integer search(List<Integer> numbers, Integer searchedNumber) {
        for (Integer number : numbers) {
            if (number.equals(searchedNumber)) {
                return searchedNumber;
            }
        }
        return searchedNumber;
    }

    private String getValueFromSupplier(Supplier<String> stringSupplier) {
        if (stringSupplier == null) {
            throw new IllegalArgumentException();
        }
        return stringSupplier.get();
    }

    @Benchmark
    public int eliminateConditionsAlwaysTrue(SimplePlan state) {
        int sum = 0;
        for (Integer number : state.numbers) {
            if (number < state.sum) {
                if(number > Integer.MIN_VALUE){
                    if (number < state.sum) {
                        if(number > Integer.MIN_VALUE){
                            sum = number + sum;
                        }
                    }
                }
            }
        }
        return sum;
    }

    @Benchmark
    public long reduceLoops(SimplePlan state) {
        long sum1 = 0;
        long sum2 = 0;
        long sum3 = 0;
        long iterations = 0;
        for (int i = 1; i < state.iterations; i++) {
            sum1 = i + 1;
        }
        for (int i = 1; i < state.iterations; i++) {
            sum2 += i;
        }
        for (int i = 1; i < state.iterations; i++) {
            sum3 -= i;
        }
        for (int i = 1; i < state.iterations; i++) {
            iterations = i;
        }
        return iterations / (sum1 + sum2 + sum3);
    }

    @Benchmark
    public long reduceSynchronizedBlocks() {
        long result = 0;

        synchronized (this) {
            long x = ThreadLocalRandom.current().nextInt();
            synchronized (this) {
                long y = ThreadLocalRandom.current().nextInt();
                synchronized (this) {
                    long z = ThreadLocalRandom.current().nextInt();
                    result = x + y + z;
                }
            }
        }

        synchronized (this) {
            long x = ThreadLocalRandom.current().nextInt();
            synchronized (this) {
                long y = ThreadLocalRandom.current().nextInt();
                synchronized (this) {
                    long z = ThreadLocalRandom.current().nextInt();
                    result = result + x - y - z;
                }
            }
        }

        return result;
    }
}
