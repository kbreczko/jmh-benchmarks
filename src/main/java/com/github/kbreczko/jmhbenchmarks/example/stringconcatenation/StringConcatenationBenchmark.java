package com.github.kbreczko.jmhbenchmarks.example.stringconcatenation;

import com.github.kbreczko.jmhbenchmarks.example.utils.RandomString;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Naiwne scalanie ciągów znaków (String).
 */

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StringConcatenationBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        String[] values;

        @Param({"10", "100", "1000"})
        int length;

        @Setup(Level.Iteration)
        public void setUp() {
            values = IntStream.range(0, 1_000)
                    .mapToObj(index -> RandomString.randomString(length))
                    .toArray(String[]::new);
        }
    }

    @Benchmark
    public String concatenateStrings(Plan plan) {
        String result = "";
        for (String value : plan.values) {
            result = result + value;
        }
        return result;
    }
}
