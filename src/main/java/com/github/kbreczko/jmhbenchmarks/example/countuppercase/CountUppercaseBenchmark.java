package com.github.kbreczko.jmhbenchmarks.example.countuppercase;

import com.github.kbreczko.jmhbenchmarks.example.utils.RandomString;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CountUppercaseBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        String value;
        int iterations;

        @Setup(Level.Iteration)
        public void setUp() {
            value = RandomString.randomString(100);
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
