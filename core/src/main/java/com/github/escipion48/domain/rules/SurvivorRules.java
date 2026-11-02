package com.github.escipion48.domain.rules;

import com.github.escipion48.domain.items.Item;

import java.util.List;
import java.util.Objects;

public record SurvivorRules(
    int initialHealth,
    int maxHealth,
    int actionsPerTurn,
    int inventoryCapacity,
    IntRange initialCount,
    PositionRange initialPosition,
    List<Item> initialItems
) {

    public SurvivorRules {
        if (initialHealth <= 0) {
            throw new IllegalArgumentException(
                "initial health must be positive");
        }

        if (maxHealth < initialHealth) {
            throw new IllegalArgumentException(
                "max health must be at least initial health");
        }

        if (actionsPerTurn <= 0) {
            throw new IllegalArgumentException(
                "actions per turn must be positive");
        }

        if (inventoryCapacity < 0) {
            throw new IllegalArgumentException(
                "inventory capacity must not be negative");
        }

        Objects.requireNonNull(initialCount, "initialCount");
        Objects.requireNonNull(initialPosition, "initialPosition");
        Objects.requireNonNull(initialItems, "initialItems");

        if (initialCount.min() < 1) {
            throw new IllegalArgumentException(
                "there must be at least one survivor");
        }

        initialItems = List.copyOf(initialItems);

        if (initialItems.size() > inventoryCapacity) {
            throw new IllegalArgumentException(
                "initial items exceed inventory capacity");
        }
    }
}
