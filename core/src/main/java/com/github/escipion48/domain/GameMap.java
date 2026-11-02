package com.github.escipion48.domain;

import com.github.escipion48.domain.exceptions.GameRuleViolationException;
import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.rules.MapRules;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.random.RandomGenerator;

public final class GameMap {

    private final MapRules rules;
    private final Map<Position, Item> items;

    private GameMap(MapRules rules) {
        this.rules = Objects.requireNonNull(rules, "rules");
        this.items = new HashMap<>();
    }

    public static GameMap createNewMap(
        MapRules rules,
        RandomGenerator random
    ) {
        Objects.requireNonNull(rules, "rules");
        Objects.requireNonNull(random, "random");

        GameMap map = new GameMap(rules);

        if (rules.itemSpawn().probability() == 0) {
            return map;
        }

        for (int x = 0; x < rules.width(); x++) {
            for (int y = 0; y < rules.height(); y++) {
                Position position = new Position(x, y);
                Optional<Item> item = rules.itemSpawn().sample(random);

                if (item.isPresent()) {
                    map.placeItem(position, item.get());
                }
            }
        }

        return map;
    }

    public boolean contains(Position position) {
        return rules.contains(position);
    }

    public Optional<Item> getItemAt(Position position) {
        requireInsideMap(position);

        return Optional.ofNullable(items.get(position));
    }

    public boolean hasItemAt(Position position) {
        requireInsideMap(position);

        return items.containsKey(position);
    }

    public void placeItem(Position position, Item item) {
        requireInsideMap(position);
        Objects.requireNonNull(item, "item");

        if (items.containsKey(position)) {
            throw new GameRuleViolationException(
                "position already contains an item");
        }

        items.put(position, item);
    }

    public Item removeItemAt(Position position) {
        requireInsideMap(position);

        Item item = items.get(position);

        if (item == null) {
            throw new GameRuleViolationException(
                "position does not contain an item");
        }

        items.remove(position);

        return item;
    }

    private void requireInsideMap(Position position) {
        Objects.requireNonNull(position, "position");

        if (!rules.contains(position)) {
            throw new IllegalArgumentException(
                "position must be inside the map");
        }
    }

    public int getWidth() {
        return rules.width();
    }

    public int getHeight() {
        return rules.height();
    }

    public Map<Position, Item> getItems() {
        return Map.copyOf(items);
    }
}
