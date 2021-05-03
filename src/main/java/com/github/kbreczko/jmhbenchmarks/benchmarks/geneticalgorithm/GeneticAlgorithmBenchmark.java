package com.github.kbreczko.jmhbenchmarks.benchmarks.geneticalgorithm;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Przykłądowa implementacja algorytmu genetycznego.
 * Źródło:  https://github.com/eugenp/tutorials/tree/master/algorithms-genetic
 */

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class GeneticAlgorithmBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        int populationSize;
        String solution;
        int maxGenerations;

        @Setup(Level.Iteration)
        public void setUp() {
            this.solution = "1011000100000100010000100000100111001000000100000100000000001111";
            this.populationSize = 50;
            this.maxGenerations = 50;
        }
    }

    @Benchmark
    public Result runAlgorithm(Plan plan) {
        final SimpleGeneticAlgorithm simpleGeneticAlgorithm = new SimpleGeneticAlgorithm();
        return simpleGeneticAlgorithm.runAlgorithm(plan.populationSize, plan.solution, plan.maxGenerations);
    }
}
