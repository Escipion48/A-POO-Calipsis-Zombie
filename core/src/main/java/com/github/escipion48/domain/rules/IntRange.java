package com.github.escipion48.domain.rules;

import java.util.Objects;
import java.util.random.RandomGenerator;

public record IntRange(int min, int max) {

    public IntRange {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
    }

    public static IntRange fixed(int value) {
        return new IntRange(value, value);
    }

    public int sample(RandomGenerator random) {
        Objects.requireNonNull(random, "random");

        if (min == max) {
            return min;
        }

        return (int) random.nextLong(min, (long) max + 1);
    }
}
