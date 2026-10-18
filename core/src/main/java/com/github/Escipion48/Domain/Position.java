package com.github.Escipion48.Domain;

public record Position(int x, int y) {

    public Position {
        if (x < 0) throw new IllegalArgumentException("Position coordenates must not be null");
        if (y < 0) throw new IllegalArgumentException("Position coordenates must not be null");
    }
}
