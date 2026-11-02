package com.github.escipion48.domain;

import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.rules.GameRules;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public record GameSnapshot(
    GameRules rules,
    long seed,
    long turn,
    long revision,
    Game.Status status,
    Game.Phase phase,
    Position exit,
    Map<Position, Item> mapItems,
    List<SurvivorView> survivors,
    List<ZombieView> zombies
) {

    public GameSnapshot {
        Objects.requireNonNull(rules, "rules");
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(exit, "exit");

        if (turn < 1 || revision < 0) {
            throw new IllegalArgumentException(
                "invalid turn or revision");
        }

        if (!rules.map().contains(exit)) {
            throw new IllegalArgumentException(
                "exit must be inside the map");
        }

        mapItems = Map.copyOf(
            Objects.requireNonNull(mapItems, "mapItems"));

        survivors = List.copyOf(
            Objects.requireNonNull(survivors, "survivors"));

        zombies = List.copyOf(
            Objects.requireNonNull(zombies, "zombies"));
    }

    public record SurvivorView(
        long id,
        int health,
        int actionsRemaining,
        Position position,
        List<Item> items
    ) {
        public SurvivorView {
            if (id <= 0 || health < 0 || actionsRemaining < 0) {
                throw new IllegalArgumentException(
                    "invalid survivor state");
            }

            if (health == 0 && actionsRemaining != 0) {
                throw new IllegalArgumentException(
                    "dead survivor cannot have actions");
            }

            Objects.requireNonNull(position, "position");
            items = List.copyOf(
                Objects.requireNonNull(items, "items"));
        }

        public boolean isAlive() {
            return health > 0;
        }
    }

    public record ZombieView(
        long id,
        int health,
        int actionsRemaining,
        int actionsPerTurn,
        int damage,
        Position position
    ) {
        public ZombieView {
            if (id <= 0
                || health < 0
                || actionsPerTurn <= 0
                || damage <= 0
                || actionsRemaining < 0
                || actionsRemaining > actionsPerTurn) {

                throw new IllegalArgumentException(
                    "invalid zombie state");
            }

            if (health == 0 && actionsRemaining != 0) {
                throw new IllegalArgumentException(
                    "dead zombie cannot have actions");
            }

            Objects.requireNonNull(position, "position");
        }

        public boolean isAlive() {
            return health > 0;
        }
    }
}
