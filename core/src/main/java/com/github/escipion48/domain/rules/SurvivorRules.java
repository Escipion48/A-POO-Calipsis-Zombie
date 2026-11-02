package com.github.escipion48.domain.rules;

import com.github.escipion48.domain.Position;
import com.github.escipion48.domain.items.Item;

import java.util.List;
import java.util.Objects;

public record SurvivorRules(
    int initialSurvivorsHealth,
    int maxSurvivorsHealth,
    int movesPerTurn,
    Position survivorsInitialPosition,
    List<Item> initialSurvivorsItems
) {
    public SurvivorRules {
        if(initialSurvivorsHealth <= 0) throw new IllegalArgumentException("survivors health must be at least 1");
        if(maxSurvivorsHealth <= 0) throw new IllegalArgumentException("max health must be at least 1");
        if(initialSurvivorsHealth > maxSurvivorsHealth) throw new IllegalArgumentException("survivors health must not be greater than max health");
        if(movesPerTurn <= 0) throw new IllegalArgumentException("moves per turn must be at least 1");
        Objects.requireNonNull(survivorsInitialPosition, "initial position must not be null");
        Objects.requireNonNull(initialSurvivorsItems, "initial survivor items must not be null");
        initialSurvivorsItems = List.copyOf(initialSurvivorsItems);
    }
}

