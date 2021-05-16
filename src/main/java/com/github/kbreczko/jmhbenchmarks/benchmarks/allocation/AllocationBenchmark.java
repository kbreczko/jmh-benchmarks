package com.github.kbreczko.jmhbenchmarks.benchmarks.allocation;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Celem testu wydajnościowego jest zapełnienie 70% sterty obiektami o rozmiarach: 1KB, 2KB, 10KB, 100KB, 1MB, 2MB, 10MB i 100MB.
 * Celem jest porównanie modułów GC.
 */
@BenchmarkMode(value = {Mode.AverageTime})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class AllocationBenchmark {

    @Benchmark
    public void createNewObjects(Plan plan, Blackhole blackhole) {
        for (int i = 0; i < plan.numberOfObjects * 4; i++) {
            blackhole.consume(new byte[plan.size]);
        }
    }

    //@Benchmark
    public void fillHeap(Plan plan, Blackhole blackhole) {
        for (int iter = 0; iter < 4; iter++) {
            List<byte[]> objects = new ArrayList<>(plan.numberOfObjects);
            for (int i = 0; i < plan.numberOfObjects; i++) {
                objects.add(new byte[plan.size]);
            }
            blackhole.consume(objects);
        }
    }

    @State(Scope.Benchmark)
    public static class Plan {
        @Param({"512", "1024", "2048", "10240", "102400", "1048576", "2097152", "10485760", "104857600"})
        // 1KB, 10KB, 100KB, 1MB, 2MB, 10MB, 100MB
        public int size;

        public int numberOfObjects;

        @Setup(Level.Iteration)
        public void setUp() {
            final long maxHeap = Runtime.getRuntime().maxMemory();
            numberOfObjects = (int) ((maxHeap * 0.70) / size);
            System.out.println("MaxHeap:" + maxHeap + ", numberOfObjects: " + numberOfObjects);
        }
    }


}
