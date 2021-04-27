package com.github.kbreczko.jmhbenchmarks.example.fibanacci;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class FibonacciBenchmark {

    @State(Scope.Benchmark)
    public static class FibonacciPlan {
        @Param({"43", "199", "521"})
        public int iterations;
    }

    @Benchmark
    public long test(FibonacciPlan fibonacciPlan) {
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
