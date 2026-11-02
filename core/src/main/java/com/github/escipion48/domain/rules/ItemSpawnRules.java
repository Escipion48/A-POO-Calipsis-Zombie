package com.github.escipion48.domain.rules;

import com.github.escipion48.domain.items.Item;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.random.RandomGenerator;

public record ItemSpawnRules(
    double probability,
    List<Item> possibleItems
) {

    public ItemSpawnRules {
        if (!Double.isFinite(probability)
            || probability < 0
            || probability > 1) {
            throw new IllegalArgumentException(
                "item probability must be finite and between 0 and 1");
        }

        Objects.requireNonNull(possibleItems, "possibleItems");

        possibleItems = List.copyOf(possibleItems);

        if (probability > 0 && possibleItems.isEmpty()) {
            throw new IllegalArgumentException(
                "possibleItems must not be empty when probability is positive");
        }
    }

    public static ItemSpawnRules none() {
        return new ItemSpawnRules(0, List.of());
    }

    public Optional<Item> sample(RandomGenerator random) {
        Objects.requireNonNull(random, "random");

        if (probability == 0 || random.nextDouble() >= probability) {
            return Optional.empty();
        }

        int index = random.nextInt(possibleItems.size());

        return Optional.of(possibleItems.get(index));
    }
}
