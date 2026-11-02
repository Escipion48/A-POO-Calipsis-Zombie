package com.github.escipion48.domain;

import com.github.escipion48.domain.exceptions.GameRuleViolationException;
import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.rules.MapRules;
import com.github.escipion48.domain.rules.SurvivorRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class Survivor extends Actor {

    private final SurvivorRules rules;
    private final List<Item> items;

    Survivor(
        long id,
        SurvivorRules rules,
        MapRules map,
        Position position
    ) {
        super(
            id,
            map,
            position,
            Objects.requireNonNull(rules, "rules").initialHealth(),
            rules.actionsPerTurn()
        );

        this.rules = rules;
        this.items = new ArrayList<>(rules.initialItems());

        startTurn();
    }

    Item itemAt(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException(
                "invalid inventory index");
        }

        return items.get(index);
    }

    void requireInventorySpace() {
        if (items.size() >= rules.inventoryCapacity()) {
            throw new GameRuleViolationException(
                "inventory is full");
        }
    }

    void addItem(Item item) {
        Objects.requireNonNull(item, "item");
        requireInventorySpace();

        items.add(item);
    }

    Item removeItem(int index) {
        itemAt(index);
        return items.remove(index);
    }

    int heal(int amount) {
        return restoreHealth(amount, rules.maxHealth());
    }

    List<Item> items() {
        return List.copyOf(items);
    }
}
