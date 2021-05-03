package com.github.kbreczko.jmhbenchmarks.benchmarks.geneticalgorithm;

public class Result {
    private final boolean founded;
    private final int generationCount;
    private final Individual individual;

    public Result(boolean founded, int generationCount, Individual individual) {
        this.founded = founded;
        this.generationCount = generationCount;
        this.individual = individual;
    }

    public static Result of(boolean founded, int generationCount, Individual individual) {
        return new Result(founded, generationCount, individual);
    }

    public boolean isFounded() {
        return founded;
    }

    public int getGenerationCount() {
        return generationCount;
    }
}
