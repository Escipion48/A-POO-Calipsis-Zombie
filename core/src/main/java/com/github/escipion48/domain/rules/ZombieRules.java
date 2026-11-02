package com.github.escipion48.domain.rules;

import java.util.Objects;

public record ZombieRules(
    IntRange initialHealth,
    IntRange movesPerTurn
) {
    public ZombieRules {
        Objects.requireNonNull(initialHealth, "initialHealth");
        Objects.requireNonNull(movesPerTurn, "movesPerTurn");

        if (initialHealth.min() <= 0) {
            throw new IllegalArgumentException(
                "initial health must be at least 1");
        }

        if (movesPerTurn.min() <= 0) {
            throw new IllegalArgumentException(
                "moves per turn must be at least 1");
        }
    }
}
