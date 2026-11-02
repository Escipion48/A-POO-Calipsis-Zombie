package com.github.escipion48.domain.rules;

import java.util.Objects;

public record GameRules(
    SurvivorRules survivors,
    ZombieRules zombies,
    MapRules map,
    PositionRange exitPosition
) {

    public GameRules {
        Objects.requireNonNull(survivors, "survivors");
        Objects.requireNonNull(zombies, "zombies");
        Objects.requireNonNull(map, "map");
        Objects.requireNonNull(exitPosition, "exitPosition");

        survivors.initialPosition().requireInside(map);
        exitPosition.requireInside(map);
    }
}
