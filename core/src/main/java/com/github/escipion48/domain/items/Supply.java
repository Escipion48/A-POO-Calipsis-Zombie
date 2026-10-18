package com.github.escipion48.domain.items;

import java.util.Objects;

public record Supply(String name, int healing) implements Item {
    public Supply {
        Objects.requireNonNull(name);
        if (healing <= 0) throw new IllegalArgumentException("healing must be positive");
    }
}
