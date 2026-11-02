package com.github.escipion48.domain;

import com.github.escipion48.domain.exceptions.GameRuleViolationException;
import com.github.escipion48.domain.exceptions.InvalidMoveException;
import com.github.escipion48.domain.exceptions.NoMovesLeftException;
import com.github.escipion48.domain.rules.GameRules;

import java.util.Objects;
import java.util.random.RandomGenerator;

public final class Zombie {

    private final int id;
    private final GameRules rules;
    private final int movesPerTurn;

    private int health;
    private int moves;
    private Position position;

    private Zombie(
        int id,
        GameRules rules,
        int health,
        int movesPerTurn,
        Position position
    ) {
        this.id = id;
        this.rules = rules;
        this.health = health;
        this.movesPerTurn = movesPerTurn;
        this.moves = movesPerTurn;
        this.position = position;
    }

    public static Zombie createNewZombie(int id, GameRules rules, RandomGenerator random) {
        Objects.requireNonNull(rules, "rules");
        Objects.requireNonNull(random, "random");

        return createNewZombie(
            id,
            rules,
            random,
            rules.map().randomPosition(random)
        );
    }

    public static Zombie createNewZombie(int id, GameRules rules, RandomGenerator random, Position spawn) {
        Objects.requireNonNull(rules, "rules");
        Objects.requireNonNull(random, "random");
        Objects.requireNonNull(spawn, "spawn");

        if (!rules.map().contains(spawn)) {
            throw new IllegalArgumentException(
                "zombie spawn must be inside the map");
        }

        return new Zombie(
            id,
            rules,
            rules.zombies().initialHealth().sample(random),
            rules.zombies().movesPerTurn().sample(random),
            spawn
        );
    }

    public boolean isAlive() {
        return health > 0;
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

    public void move(Position destination) {
        Objects.requireNonNull(destination, "destination");
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
        moves = isAlive() ? movesPerTurn : 0;
    }

    private void requireAlive() {
        if (!isAlive()) {
            throw new GameRuleViolationException("zombie is dead");
        }
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

    public int getMovesPerTurn() {
        return movesPerTurn;
    }

    public Position getPosition() {
        return position;
    }
}
