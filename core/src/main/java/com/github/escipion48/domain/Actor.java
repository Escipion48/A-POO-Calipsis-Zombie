package com.github.escipion48.domain;

import com.github.escipion48.domain.exceptions.GameRuleViolationException;
import com.github.escipion48.domain.exceptions.InvalidMoveException;
import com.github.escipion48.domain.exceptions.MaxHealthException;
import com.github.escipion48.domain.exceptions.NoMovesLeftException;
import com.github.escipion48.domain.rules.MapRules;

import java.util.Objects;

abstract class Actor {

    private final long id;
    private final MapRules map;
    private final int actionsPerTurn;

    private int health;
    private int actionsRemaining;
    private Position position;

    Actor(
        long id,
        MapRules map,
        Position position,
        int health,
        int actionsPerTurn
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }

        Objects.requireNonNull(map, "map");
        Objects.requireNonNull(position, "position");

        if (!map.contains(position)) {
            throw new IllegalArgumentException(
                "actor position must be inside the map");
        }

        if (health <= 0 || actionsPerTurn <= 0) {
            throw new IllegalArgumentException(
                "initial health and actions must be positive");
        }

        this.id = id;
        this.map = map;
        this.position = position;
        this.health = health;
        this.actionsPerTurn = actionsPerTurn;
        this.actionsRemaining = 0;
    }

    final long id() {
        return id;
    }

    final int health() {
        return health;
    }

    final int actionsRemaining() {
        return actionsRemaining;
    }

    final int actionsPerTurn() {
        return actionsPerTurn;
    }

    final Position position() {
        return position;
    }

    final boolean isAlive() {
        return health > 0;
    }

    final void requireCanAct() {
        if (!isAlive()) {
            throw new GameRuleViolationException("actor is dead");
        }

        if (actionsRemaining <= 0) {
            throw new NoMovesLeftException();
        }
    }

    final void spendAction() {
        requireCanAct();
        actionsRemaining--;
    }

    final void startTurn() {
        actionsRemaining = isAlive() ? actionsPerTurn : 0;
    }

    final void move(Position destination) {
        Objects.requireNonNull(destination, "destination");
        requireCanAct();

        if (!map.contains(destination)) {
            throw new InvalidMoveException(
                "destination must be inside the map");
        }

        if (!position.isAdjacent(destination)) {
            throw new InvalidMoveException(
                "destination must be adjacent");
        }

        spendAction();
        position = destination;
    }

    final void takeDamage(int damage) {
        if (damage <= 0) {
            throw new IllegalArgumentException(
                "damage must be positive");
        }

        if (!isAlive()) {
            throw new GameRuleViolationException(
                "cannot damage a dead actor");
        }

        health = Math.max(0, health - damage);

        if (!isAlive()) {
            actionsRemaining = 0;
        }
    }

    final int restoreHealth(int amount, int maximumHealth) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "healing amount must be positive");
        }

        if (maximumHealth <= 0 || maximumHealth < health) {
            throw new IllegalArgumentException(
                "invalid maximum health");
        }

        if (health == maximumHealth) {
            throw new MaxHealthException();
        }

        int previousHealth = health;

        health = (int) Math.min(
            (long) health + amount,
            maximumHealth
        );

        // No se restauran acciones al revivir.
        return health - previousHealth;
    }
}
