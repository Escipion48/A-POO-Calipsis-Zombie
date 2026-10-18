package com.github.escipion48.domain.items;

public sealed interface Item permits Weapon, Supply {
    String name();

}

