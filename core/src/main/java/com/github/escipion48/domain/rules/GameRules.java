package com.github.escipion48.domain.rules;

import java.util.Objects;

public record GameRules
    (
    SurvivorRules survivors,
    ZombieRules zombies,
    MapRules map
)
{
    public GameRules {
        Objects.requireNonNull(survivors, "survivors");
        Objects.requireNonNull(zombies, "zombies");
        Objects.requireNonNull(map, "map");

        if (!map.contains(survivors.survivorsInitialPosition())) {
            throw new IllegalArgumentException(
                "survivors initial position must be inside the map");
        }
    }
}
