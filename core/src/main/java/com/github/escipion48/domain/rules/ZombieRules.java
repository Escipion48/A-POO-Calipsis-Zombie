package com.github.escipion48.domain.rules;

import java.util.Objects;

public record ZombieRules(
    IntRange initialHealth,
    IntRange actionsPerTurn,
    IntRange damage,
    IntRange initialCount,
    IntRange perTurnCount,
    int minimumSpawnDistance
) {

    public ZombieRules {
        Objects.requireNonNull(initialHealth, "initialHealth");
        Objects.requireNonNull(actionsPerTurn, "actionsPerTurn");
        Objects.requireNonNull(damage, "damage");
        Objects.requireNonNull(initialCount, "initialCount");
        Objects.requireNonNull(perTurnCount, "perTurnCount");

        if (initialHealth.min() <= 0) {
            throw new IllegalArgumentException(
                "zombie health must be positive");
        }

        if (actionsPerTurn.min() <= 0) {
            throw new IllegalArgumentException(
                "zombie actions must be positive");
        }

        if (damage.min() <= 0) {
            throw new IllegalArgumentException(
                "zombie damage must be positive");
        }

        if (initialCount.min() < 0 || perTurnCount.min() < 0) {
            throw new IllegalArgumentException(
                "zombie counts must not be negative");
        }

        if (minimumSpawnDistance < 0) {
            throw new IllegalArgumentException(
                "minimum spawn distance must not be negative");
        }
    }
}
