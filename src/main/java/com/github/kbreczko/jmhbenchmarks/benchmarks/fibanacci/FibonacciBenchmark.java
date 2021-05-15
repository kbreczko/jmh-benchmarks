package com.github.kbreczko.jmhbenchmarks.benchmarks.fibanacci;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Implementacja algorytmu fibonacciego za pomocą rekurencji ogonowej.
 */

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class FibonacciBenchmark {

    @State(Scope.Benchmark)
    public static class FibonacciPlan {
        @Param({"521", "1153", "2021"})
        public int iterations;
    }

    @Benchmark
    public long runFibonacci(FibonacciPlan fibonacciPlan) {
        return fib(fibonacciPlan.iterations, 0, 1);
    }

    static long fib(long n, int a, int b) {
        if (n == 0) {
            return a;
        }
        if (n == 1) {
            return b;
        }
        return fib(n - 1, b, a + b);
    }
}
