package com.github.escipion48.domain.rules;

import com.github.escipion48.domain.Position;

import java.util.Objects;
import java.util.random.RandomGenerator;

public record MapRules(int width, int height) {

    public MapRules {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                "map dimensions must be positive");
        }
    }

    public static MapRules generate(
        IntRange widthRange,
        IntRange heightRange,
        RandomGenerator random
    ) {
        Objects.requireNonNull(widthRange, "widthRange");
        Objects.requireNonNull(heightRange, "heightRange");
        Objects.requireNonNull(random, "random");

        if (widthRange.min() <= 0 || heightRange.min() <= 0) {
            throw new IllegalArgumentException(
                "all possible map dimensions must be positive");
        }

        return new MapRules(
            widthRange.sample(random),
            heightRange.sample(random)
        );
    }

    public boolean contains(Position position) {
        Objects.requireNonNull(position, "position");

        return position.x() >= 0
            && position.x() < width
            && position.y() >= 0
            && position.y() < height;
    }

    public Position randomPosition(RandomGenerator random) {
        Objects.requireNonNull(random, "random");

        return new Position(
            random.nextInt(width),
            random.nextInt(height)
        );
    }
}
