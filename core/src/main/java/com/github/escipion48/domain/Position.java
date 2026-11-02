package com.github.escipion48.domain;

import java.util.Objects;

public record Position(int x, int y) {

    public Position {
        if (x < 0) throw new IllegalArgumentException("Position coordenates must not be negative");
        if (y < 0) throw new IllegalArgumentException("Position coordenates must not be negative");
    }

    public boolean isAdjacent(Position other) {
        Objects.requireNonNull(other, "position to check must not be null");
        int dx = Math.abs(x - other.x);
        int dy = Math.abs(y - other.y);
        return Math.max(dx, dy) == 1;
    }
}
