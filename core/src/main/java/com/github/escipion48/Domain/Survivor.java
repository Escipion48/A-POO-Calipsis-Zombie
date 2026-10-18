package com.github.escipion48.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Survivor {

    private final int id;
    private final GameRules rules;
    private int health;
    private int moves;
    private Position position;
    private final List<Item> items;

    private Survivor(int id, GameRules rules, int health, int moves, Position position, List<Item> items) {
        this.id = id;
        this.rules = rules;
        this.health = health;
        this.moves = moves;
        this.position = position;
        this.items = items;
    }

    public static Survivor createNew(int id,GameRules rules) {
        Objects.requireNonNull(rules, "rules must not be null");

        return new Survivor(
            id,
            rules,
            rules.initialSurvivorsHealth(),
            rules.movesPerTurn(),
            rules.survivorsInitialPosition(),
            new ArrayList<>(rules.initialSurvivorsItems())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Survivor survivor)) return false;
        return id == survivor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
