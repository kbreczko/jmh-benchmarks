package com.github.kbreczko.jmhbenchmarks.example.creator;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class CreatorBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        public int iterations;

        @Setup(Level.Iteration)
        public void setUp() {
            this.iterations = 10_000_000;
        }
    }

    static class Product {
        public void doNothing() {

        }
    }

    @Benchmark
    public void createNewObjects(Plan plan, Blackhole blackhole) {
        for (int i = 0; i < plan.iterations; i++) {
            final Product product = new Product();
            product.doNothing();
            blackhole.consume(product);
        }
    }
}
