package com.github.kbreczko.jmhbenchmarks.benchmarks.levenshtein;

import java.util.Arrays;

public class LevenshteinDistance {

    public int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    final int cost1 = dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1));
                    final int cost2 = dp[i - 1][j] + 1;
                    final int cost3 = dp[i][j - 1] + 1;
                    dp[i][j] = min(cost1, cost2, cost3);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }


    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
}
