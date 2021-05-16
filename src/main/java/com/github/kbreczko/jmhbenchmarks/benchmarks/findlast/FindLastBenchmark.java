package com.github.kbreczko.jmhbenchmarks.benchmarks.findlast;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(value = {Mode.AverageTime, Mode.Throughput})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class FindLastBenchmark {

    //@Benchmark
    public int findLastPrefixUsingScanner1(FindLastState state) {
        final FindLastPrefixScanner1 scanner = new FindLastPrefixScanner1(state.searchedText);

        for (int i = 0; i < state.size; i++) {
            final String name = state.names[i];
            final int value = state.values[i];
            scanner.consume(name);
            scanner.process(value);
        }

        return scanner.getLastValue();
    }

    //@Benchmark
    public int findLastPrefixUsingScanner2(FindLastState state) {
        final FindLastPrefixScanner2 scanner = new FindLastPrefixScanner2(state.searchedText);

        for (int i = 0; i < state.size; i++) {
            final String name = state.names[i];
            final int value = state.values[i];
            scanner.consume(name);
            scanner.process(value);
        }

        return scanner.getLastValue();
    }
}
