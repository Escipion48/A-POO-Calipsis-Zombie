package com.github.escipion48.domain;

import com.github.escipion48.domain.exceptions.SurvivorDeadException;
import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.items.Supply;
import com.github.escipion48.domain.exceptions.InvalidMoveException;
import com.github.escipion48.domain.exceptions.MaxHealthException;
import com.github.escipion48.domain.exceptions.NoMovesLeftException;

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

    public static Survivor createNewSurvivor(int id, GameRules rules) {
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

    public boolean isAlive(){
        return health > 0;
    }

    public void heal(int amount){
        if(amount <= 0) throw new IllegalArgumentException("heal amount must not be negative or 0");
        if(health == rules.maxSurvivorsHealth()) throw new MaxHealthException();
        health = Math.min(health + amount, rules.maxSurvivorsHealth());
    }

    public void consume(Supply supply) {
        requireAlive();
        Objects.requireNonNull(supply, "supply must not be null");
        requireOwned(supply);
        heal(supply.healing());
        items.remove(supply);
    }

    public void move(Position destination){
        requireAlive();
        Objects.requireNonNull(destination, "destination must not be null");
        if(moves <= 0) throw new NoMovesLeftException();
        if(!this.position.isAdjacent(destination)) throw new InvalidMoveException("cannot move to a not adjacent position");
        moves--;
        this.position = destination;
    }

    public void startTurn(){

        moves = isAlive() ? rules.movesPerTurn() : 0;

    }

    private void requireOwned(Item item) {
        if (!items.contains(item)) throw new IllegalArgumentException("survivor does not have that item");
    }

    private void requireAlive(){
        if(!isAlive()) throw new SurvivorDeadException();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Survivor survivor)) return false;
        return id == survivor.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
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
