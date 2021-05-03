package com.github.kbreczko.jmhbenchmarks.benchmarks.findlast;

import com.github.kbreczko.jmhbenchmarks.benchmarks.utils.RandomString;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class FindLastState {
    @Param({"10", "100", "1000"})
    int size;

    String[] names;
    int[] values;

    String searchedText;

    @Setup(Level.Iteration)
    public void setUp() {
        values = IntStream.range(0, size)
                .map(index -> randomValue())
                .toArray();
        names = IntStream.range(0, size)
                .mapToObj(index -> RandomString.randomString(10))
                .toArray(String[]::new);
        searchedText = RandomString.randomString(10);
    }

    private int randomValue() {
        return ThreadLocalRandom.current().nextInt();
    }
}
