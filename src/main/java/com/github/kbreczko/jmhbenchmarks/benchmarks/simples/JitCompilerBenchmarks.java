package com.github.kbreczko.jmhbenchmarks.benchmarks.simples;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Zbiór testów utworzonych pod optymalizację kodu przez kompilator JIT.
 */
@BenchmarkMode(value = {Mode.AverageTime, Mode.Throughput})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class JitCompilerBenchmarks {

    @State(Scope.Benchmark)
    public static class SimplePlan {
        public List<Integer> numbers;
        public long sum;
        public int iterations;
        public int integerMaxValue;

        @Setup(Level.Iteration)
        public void setUp() {
            this.numbers = IntStream.range(0, 1_000_000)
                    .map(value -> ThreadLocalRandom.current().nextInt(10) + 1)
                    .boxed()
                    .collect(Collectors.toList());
            this.sum = Long.MAX_VALUE;
            this.iterations = 1_000_000;
            this.integerMaxValue = Integer.MAX_VALUE;
        }
    }

    private static class RandomNumber {
        private final int number;

        public RandomNumber() {
            this.number = ThreadLocalRandom.current().nextInt(10);
        }

        public int getNumber() {
            return number;
        }

    }

    /**
     * Cel: Wyciągnięcie wyrażeń poza pętle
     */
    @Benchmark
    public double extractOperationOutOfLoop(SimplePlan state) {
        double sum = 0;
        double result = 0;
        double x = 100;
        for (int i = 1; i < state.iterations; i++) {
            double v1 = (Math.log((i + 1) * x) + Math.log(x)) * 2;
            double v2 = Math.log((i + 1) * x) * Math.log(x);
            sum += v1 / v2;
            if (i == state.iterations - 1) {
                result = Math.log(sum);
            }
        }
        return result;
    }

    /**
     * Cel: Usunięcie nieużywanego kodu
     */
    @Benchmark
    public String redundantCode(SimplePlan state) {
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

    /**
     * Cel: Usunięcie zawsze prawdziwych warunków
     */
    @Benchmark
    public long eliminateConditionsAlwaysTrue(SimplePlan state) {
        long sum = 0;
        for (Integer number : state.numbers) {
            if (number == null) {
                return Integer.MIN_VALUE;
            }

            if (number > state.sum && number < Integer.MAX_VALUE) {
                return number;
            }

            if (number < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

            if (sum < Long.MAX_VALUE) {
                if (sum > Long.MIN_VALUE) {
                    sum = number + sum;
                }
            }

        }
        return sum;
    }

    /**
     * Cel: zredukowanie pętli
     */
    @Benchmark
    public long reduceLoops(SimplePlan state) {
        long sum1 = 0;
        long sum2 = 0;
        long sum3 = 0;
        long iterations = 0;
        for (int i = 1; i < state.integerMaxValue; i++) {
            sum1 = i + 1;
        }
        for (int i = 1; i < state.integerMaxValue; i++) {
            sum2 += i;
        }
        for (int i = 1; i < state.integerMaxValue; i++) {
            sum3 -= i;
        }
        for (int i = 1; i < state.integerMaxValue; i++) {
            iterations = i;
        }
        return iterations / (sum1 + sum2 + sum3);
    }

    /**
     * Sumuje wygenerowane liczby w oddzielnych blokach synchronicznych. Cel: Zredukowanie bloków synchronicznych.
     */
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

    /**
     * Tworzy nowe instancje wewnątrz w scope i sumuje losowa wartości. Cel: optymalizacja na podstawie krótkiego czasu życia obiektu
     * i dostępność w małych scopie.
     */
    @Benchmark
    public long reduceYoungObjects(SimplePlan state) {
        long sum = 0;
        for (int i = 0; i < state.iterations; i++) {
            final RandomNumber randomNumber1 = new RandomNumber();
            final RandomNumber randomNumber2 = new RandomNumber();
            sum += randomNumber1.getNumber() + randomNumber2.getNumber();
        }
        return sum;
    }
}
