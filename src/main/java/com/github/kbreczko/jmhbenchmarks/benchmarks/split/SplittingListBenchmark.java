package com.github.kbreczko.jmhbenchmarks.benchmarks.split;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Nowe API z Javy 11 vs Java 8. Test ma na celu przepakowanie listy do niemodyfikowalnych zbiorów 10-elementowych.
 */
@BenchmarkMode(value = {Mode.AverageTime})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SplittingListBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        public int[] numbers;

        @Setup(Level.Iteration)
        public void setUp() {
            numbers = IntStream.range(0, 1_000_000)
                    .map(value -> ThreadLocalRandom.current().nextInt())
                    .toArray();
        }
    }

    @Benchmark
    public List<Set<Integer>> split(Plan plan) {
        final List<Set<Integer>> result = new LinkedList<>();
        final int[] numbers = plan.numbers;
        for (int i = 0; i < numbers.length; i = i + 10) {
            final Set<Integer> values = toSet(numbers[i], numbers[i + 1], numbers[i + 2], numbers[i + 3], numbers[i + 4], numbers[i + 5], numbers[i + 6], numbers[i + 7], numbers[i + 8], numbers[i + 9]);
            result.add(values);
        }
        return result;
    }

//    private <T> Set<T> toSet(T v1, T v2, T v3, T v4, T v5, T v6, T v7, T v8, T v9, T v10) {
//        return Set.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
//    }

    private <T> Set<T> toSet(T v1, T v2, T v3, T v4, T v5, T v6, T v7, T v8, T v9, T v10) {
        Set<T> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        set.add(v3);
        set.add(v4);
        set.add(v5);
        set.add(v6);
        set.add(v7);
        set.add(v8);
        set.add(v9);
        set.add(v10);
        return Collections.unmodifiableSet(set);
    }
}
