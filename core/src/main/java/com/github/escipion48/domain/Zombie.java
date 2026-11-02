package com.github.escipion48.domain;

import com.github.escipion48.domain.rules.MapRules;
import com.github.escipion48.domain.rules.ZombieRules;

import java.util.Objects;
import java.util.random.RandomGenerator;

final class Zombie extends Actor {

    private final int damage;

    Zombie(
        long id,
        MapRules map,
        Position position,
        int health,
        int actionsPerTurn,
        int damage
    ) {

        if (damage <= 0) {
            throw new IllegalArgumentException(
                "zombie damage must be positive");
        }

        super(id, map, position, health, actionsPerTurn);


        this.damage = damage;
    }

    static Zombie create(
        long id,
        MapRules map,
        ZombieRules rules,
        RandomGenerator random,
        Position position
    ) {
        Objects.requireNonNull(map, "map");
        Objects.requireNonNull(rules, "rules");
        Objects.requireNonNull(random, "random");
        Objects.requireNonNull(position, "position");

        if (id <= 0 || !map.contains(position)) {
            throw new IllegalArgumentException(
                "invalid zombie id or spawn position");
        }

        return new Zombie(
            id,
            map,
            position,
            rules.initialHealth().sample(random),
            rules.actionsPerTurn().sample(random),
            rules.damage().sample(random)
        );
    }

    int damage() {
        return damage;
    }
}
