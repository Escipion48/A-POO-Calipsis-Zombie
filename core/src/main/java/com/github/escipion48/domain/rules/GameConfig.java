package com.github.escipion48.domain.rules;

import java.util.Objects;
import java.util.random.RandomGenerator;

public record GameConfig(
    SurvivorRules survivors,
    ZombieRules zombies,
    IntRange width,
    IntRange height,
    ItemSpawnRules itemSpawn,
    PositionRange exitPosition
) {

    public GameConfig {
        Objects.requireNonNull(survivors, "survivors");
        Objects.requireNonNull(zombies, "zombies");
        Objects.requireNonNull(width, "width");
        Objects.requireNonNull(height, "height");
        Objects.requireNonNull(itemSpawn, "itemSpawn");
        Objects.requireNonNull(exitPosition, "exitPosition");

        if (width.min() <= 0 || height.min() <= 0) {
            throw new IllegalArgumentException(
                "map dimensions must always be positive");
        }
    }

    public GameRules resolve(RandomGenerator random) {
        Objects.requireNonNull(random, "random");

        MapRules map = MapRules.generate(
            width,
            height,
            itemSpawn,
            random
        );

        return new GameRules(
            survivors,
            zombies,
            map,
            exitPosition
        );
    }
}
