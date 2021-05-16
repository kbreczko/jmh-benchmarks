package com.github.kbreczko.jmhbenchmarks.benchmarks.stream;

import org.apache.commons.math3.util.Pair;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Testy mają na celu sprawdzenie wydajności streamów.
 */
@BenchmarkMode(value = {Mode.AverageTime})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StreamBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        public int[] array;

        @Setup(Level.Iteration)
        public void setUp() {
            Random rn = new Random();
            array = IntStream.range(0, 1_000_000)
                    .map(value -> rn.nextInt(10) + 1)
                    .toArray();
        }
    }

    //@Benchmark
    public List<Integer> sort(Plan plan) {
        return Arrays.stream(plan.array)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
    }

    //@Benchmark
    public List<Pair<Integer, Integer>> mapToPair(Plan plan) {
        return IntStream.range(0, plan.array.length)
                .boxed()
                .map(index -> new Pair<>(index, plan.array[index]))
                .collect(Collectors.toList());
    }

    //@Benchmark
    public List<Integer> plusOne(Plan plan) {
        return Arrays.stream(plan.array)
                .map(number -> number + 1)
                .boxed()
                .collect(Collectors.toList());
    }

}
