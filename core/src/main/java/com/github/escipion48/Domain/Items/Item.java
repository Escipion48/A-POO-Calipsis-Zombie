package com.github.escipion48.Domain.Items;

public sealed interface Item permits Weapon, Supply {
    String name();

}

