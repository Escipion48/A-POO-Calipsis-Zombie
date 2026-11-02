package com.github.escipion48.domain.rules;

import com.github.escipion48.domain.Position;

import java.util.Objects;
import java.util.random.RandomGenerator;

public record PositionRange(IntRange x, IntRange y) {

    public PositionRange {
        Objects.requireNonNull(x, "x");
        Objects.requireNonNull(y, "y");

        if (x.min() < 0 || y.min() < 0) {
            throw new IllegalArgumentException(
                "position ranges must not contain negative coordinates");
        }
    }

    public static PositionRange fixed(Position position) {
        Objects.requireNonNull(position, "position");

        return new PositionRange(
            IntRange.fixed(position.x()),
            IntRange.fixed(position.y())
        );
    }

    public Position sample(RandomGenerator random) {
        Objects.requireNonNull(random, "random");

        return new Position(x.sample(random), y.sample(random));
    }

    public void requireInside(MapRules map) {
        Objects.requireNonNull(map, "map");

        if (x.max() >= map.width() || y.max() >= map.height()) {
            throw new IllegalArgumentException(
                "every position in the range must fit inside the map");
        }
    }
}
