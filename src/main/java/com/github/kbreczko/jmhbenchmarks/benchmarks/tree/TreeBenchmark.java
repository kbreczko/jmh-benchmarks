package com.github.kbreczko.jmhbenchmarks.benchmarks.tree;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Drzewo jest zrównoważonym drzewem binarnym, w którym każdy wierzchołem zajmuje conajmniej 1KB danych.
 * Pojedyńcze drzewo ma 1024 dzieci + 1023 rodziców co nam daje około 2MB danych.
 * Podczas iteracji głównej pętli for tworzymy 1024 drzew, czyli powinno zaalokować conajmniej połowe pamięci RAM z 4GB, czyli 2GB.
 */

@BenchmarkMode(value = {Mode.All})
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 15)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class TreeBenchmark {

    @State(Scope.Benchmark)
    public static class Plan {
        public int size;

        public int numberOfObjects;

        @Setup(Level.Iteration)
        public void setUp() {
            size = 1024;
            numberOfObjects = 1024;
        }
    }

    static class Product {
        private final Product product1;
        private final Product product2;
        private final int total;
        private final byte[] bytes;

        public Product(Product product1, Product product2, int total, byte[] bytes) {
            this.product1 = product1;
            this.product2 = product2;
            this.total = total;
            this.bytes = bytes;
        }

        public static Product createRecursive(int total) {
            if (total <= 1) {
                return new Product(null, null, total, new byte[1024]);
            }

            final Product product1 = Product.createRecursive((total / 2));
            final Product product2 = Product.createRecursive((total / 2));
            return new Product(product1, product2, total, new byte[1024]);
        }
    }

    @Benchmark
    public void createNewObjectsWithRecursive(Plan plan, Blackhole blackhole) {
        for (int iter = 0; iter < 4; iter++) {
            List<Product> objects = new ArrayList<>(plan.numberOfObjects);
            for (int i = 0; i < plan.numberOfObjects; i++) {
                objects.add(Product.createRecursive(plan.size));
            }
            blackhole.consume(objects);
        }
    }
}
