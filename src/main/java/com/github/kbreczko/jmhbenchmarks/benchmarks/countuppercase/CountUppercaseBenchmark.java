package com.github.kbreczko.jmhbenchmarks.benchmarks.countuppercase;

import com.github.kbreczko.jmhbenchmarks.benchmarks.utils.RandomString;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Zliczanie N razy wielkich liter ciągu znaków (string) przekazanego poprzez argument.
 */
@BenchmarkMode(value = {Mode.AverageTime})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class CountUppercaseBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        String value;

        int iterations;

        @Param({"100", "1000"})
        int length;

        @Setup(Level.Iteration)
        public void setUp() {
            value = RandomString.randomString(length);
            iterations = 1_000_000;
        }
    }

    @Benchmark
    public long countUppercase(Plan plan) {
        long total = 0;
        for (int i = 0; i < plan.iterations; i++) {
            total += plan.value.chars().filter(Character::isUpperCase).count();
        }
        return total;
    }
}
