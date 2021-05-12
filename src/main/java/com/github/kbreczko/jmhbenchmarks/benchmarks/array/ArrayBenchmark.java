package com.github.kbreczko.jmhbenchmarks.benchmarks.array;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


/**
 * Testy mają na celu sprawdzenie wydajności aplikacji podczas odczytywania, zapisywania i zamianę elemntów miejscami w tablicy.
 */
@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ArrayBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        public int[] array;
        public int[] unsortedArray;
        public int element;

        @Setup(Level.Iteration)
        public void setUp() {
            Random rn = new Random();
            array = IntStream.range(0, 1_000_000)
                    .map(value -> rn.nextInt(10) + 1)
                    .toArray();
            element = Integer.MAX_VALUE / 2;
        }

        @Setup(Level.Trial)
        public void setUpTrial() {
            unsortedArray = IntStream.range(0, 1000)
                    .map(value -> ThreadLocalRandom.current().nextInt())
                    .toArray();
        }
    }

    @Benchmark
    public long read(Plan plan) {
        long sum = 0;
        for (int i = 0; i < plan.array.length; i++) {
            sum += plan.array[i];
        }
        return sum;
    }

    @Benchmark
    public int[] write(Plan plan) {
        for (int i = 0; i < plan.array.length; i++) {
            plan.array[i] = plan.element;
        }
        return plan.array;
    }

    @Benchmark
    public int[] readAndWrite(Plan plan) {
        for (int i = 0; i < plan.array.length; i++) {
            plan.array[i] = plan.element + plan.array[i];
        }
        return plan.array;
    }

    @Benchmark
    public int[] swap(Plan plan) {
        final int[] unsortedArray = plan.unsortedArray;
        final int length = unsortedArray.length;

        for (int i = 0; i < length; i++) {
            for (int j = 1; j < (length - i); j++) {
                if (unsortedArray[j - 1] > unsortedArray[j]) {
                    final int temp = unsortedArray[j - 1];
                    unsortedArray[j - 1] = unsortedArray[j];
                    unsortedArray[j] = temp;
                }
            }
        }
        return unsortedArray;
    }

}