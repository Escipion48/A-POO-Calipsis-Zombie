package com.github.escipion48.Domain.Items;

import java.util.Objects;

public record Weapon(String name, int damage, int range) implements Item {
    public Weapon {
        Objects.requireNonNull(name);
        if (damage <= 0) throw new IllegalArgumentException("damage must be positive");
        if (range <= 0) throw new IllegalArgumentException("range must be positive");
    }
}
