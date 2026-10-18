package com.github.escipion48.domain;

import java.util.Objects;

public record Position(int x, int y) {

    public Position {
        if (x < 0) throw new IllegalArgumentException("Position coordenates must not be null");
        if (y < 0) throw new IllegalArgumentException("Position coordenates must not be null");
    }

    public Boolean isAdjacent(Position position){
        Objects.requireNonNull(position, "position to check must not be null");
        return
            (this.x != position.x() && this.y != position.y())
                &&
                (this.x - position.x() <= 0 || this.y - position.y() <= 0);
    }
}
