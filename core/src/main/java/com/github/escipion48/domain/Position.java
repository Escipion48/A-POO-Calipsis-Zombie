package com.github.escipion48.domain;

import java.util.Objects;

public record Position(int x, int y) {

    public Position {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException(
                "position coordinates must not be negative");
        }
    }

    public int distanceTo(Position other) {
        Objects.requireNonNull(other, "other");

        return Math.max(
            Math.abs(x - other.x),
            Math.abs(y - other.y)
        );
    }

    public boolean isAdjacent(Position other) {
        return distanceTo(other) == 1;
    }
}
