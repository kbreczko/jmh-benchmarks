package com.github.kbreczko.jmhbenchmarks.benchmarks.allocation;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Celem testu wydajnościowego jest zapełnienie 60% sterty obiektami o rozmiarach: 1KB, 10KB, 100KB, 1MB, 10MB i 100MB.
 * Celem jest porównanie modułów GC.
 */
@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class AllocationBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        @Param({"1024", "10240", "102400", "1048576", "10485760", "104857600"}) // 1KB, 10KB, 100KB, 1MB, 10MB, 100MB
        public int size;

        public int numberOfObjects;

        @Setup(Level.Iteration)
        public void setUp() {
            final long maxHeap = this.convertGigaByteToByte(2);
            numberOfObjects = (int) ((maxHeap * 0.60) / size);
        }

        private long convertGigaByteToByte(int sizeInGigaBytes) {
            return ((sizeInGigaBytes * 1024L) * 1024L) * 1024L;
        }
    }

    @Benchmark
    public byte[] createNewObjects(Plan plan, Blackhole blackhole) {
        byte[] result = new byte[1];
        for (int i = 0; i < plan.numberOfObjects; i++) {
            final byte[] bytes = new byte[plan.size];
            blackhole.consume(bytes);
            result = bytes;
        }
        return result;
    }

    @Benchmark
    public void fillHeap(Plan plan, Blackhole blackhole) {
        for (int iter = 0; iter < 2; iter++) {
            List<byte[]> objects = new ArrayList<>(plan.numberOfObjects);
            for (int i = 0; i < plan.numberOfObjects; i++) {
                objects.add(new byte[plan.size]);
            }
            blackhole.consume(objects);
        }
    }


}
