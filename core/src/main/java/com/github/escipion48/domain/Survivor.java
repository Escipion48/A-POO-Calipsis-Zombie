package com.github.escipion48.domain;

import com.github.escipion48.domain.exceptions.InvalidMoveException;
import com.github.escipion48.domain.exceptions.MaxHealthException;
import com.github.escipion48.domain.exceptions.NoMovesLeftException;
import com.github.escipion48.domain.exceptions.SurvivorDeadException;
import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.items.Supply;
import com.github.escipion48.domain.rules.GameRules;
import com.github.escipion48.domain.rules.SurvivorRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Survivor {

    private final int id;
    private final GameRules rules;
    private final List<Item> items;
    private int health;
    private int moves;
    private Position position;

    private Survivor(
        int id,
        GameRules rules,
        int health,
        int moves,
        Position position,
        List<Item> items
    ) {
        this.id = id;
        this.rules = rules;
        this.health = health;
        this.moves = moves;
        this.position = position;
        this.items = items;
    }

    public static Survivor createNewSurvivor(int id, GameRules rules) {
        Objects.requireNonNull(rules, "rules must not be null");

        SurvivorRules survivorRules = rules.survivors();

        return new Survivor(
            id,
            rules,
            survivorRules.initialSurvivorsHealth(),
            survivorRules.movesPerTurn(),
            survivorRules.survivorsInitialPosition(),
            new ArrayList<>(survivorRules.initialSurvivorsItems())
        );
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void heal(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "heal amount must be positive");
        }

        int maxHealth = rules.survivors().maxSurvivorsHealth();

        if (health == maxHealth) {
            throw new MaxHealthException();
        }

        health = (int) Math.min((long) health + amount, maxHealth);
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
            throw new IllegalArgumentException(
                "damage must be positive");
        }

        health = Math.max(0, health - damage);

        if (!isAlive()) {
            moves = 0;
        }
    }

    public void consume(Supply supply) {
        Objects.requireNonNull(supply, "supply must not be null");
        requireAlive();
        requireOwned(supply);

        heal(supply.healing());
        items.remove(supply);
        moves--;
    }

    public void move(Position destination) {
        Objects.requireNonNull(
            destination,
            "destination must not be null");

        requireAlive();

        if (moves <= 0) {
            throw new NoMovesLeftException();
        }

        if (!rules.map().contains(destination)) {
            throw new InvalidMoveException(
                "destination must be inside the map");
        }

        if (!position.isAdjacent(destination)) {
            throw new InvalidMoveException(
                "destination must be adjacent");
        }

        position = destination;
        moves--;
    }

    public void startTurn() {
        moves = isAlive() ? rules.survivors().movesPerTurn() : 0;
    }

    private void requireOwned(Item item) {
        if (!items.contains(item)) {
            throw new IllegalArgumentException(
                "survivor does not have that item");
        }
    }

    private void requireAlive() {
        if (!isAlive()) {
            throw new SurvivorDeadException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Survivor survivor)) {
            return false;
        }

        return id == survivor.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public int getMoves() {
        return moves;
    }

    public Position getPosition() {
        return position;
    }

    public List<Item> getItems() {
        return List.copyOf(items);
    }
}
