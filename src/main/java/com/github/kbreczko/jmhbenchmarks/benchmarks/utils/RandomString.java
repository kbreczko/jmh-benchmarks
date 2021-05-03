package com.github.kbreczko.jmhbenchmarks.benchmarks.utils;

import java.util.Random;

public class RandomString {

    public static String randomString(int length){
        int leftLimit = 65; // letter 'A'
        int rightLimit = 122; // letter 'z'

        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(value -> value <= 90 || value >= 97)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
