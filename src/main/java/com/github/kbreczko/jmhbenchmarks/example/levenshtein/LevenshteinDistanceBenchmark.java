package com.github.kbreczko.jmhbenchmarks.example.levenshtein;

import com.github.kbreczko.jmhbenchmarks.example.utils.RandomString;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Test wykonany na podstawie algorytmu z https://www.baeldung.com/java-levenshtein-distance
 */
@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class LevenshteinDistanceBenchmark {

    @State(Scope.Benchmark)
    private static class Plan {
        public String value1;
        public String value2;

        @Param({"100", "1000", "10000"})
        int length;

        @Setup(Level.Iteration)
        public void setUp() {
            value1 = RandomString.randomString(length);
            value2 = RandomString.randomString(length);
        }
    }

    @Benchmark
    public int runAlgorithm(Plan plan) {
        return new LevenshteinDistance().calculate(plan.value1, plan.value2);
    }
}
