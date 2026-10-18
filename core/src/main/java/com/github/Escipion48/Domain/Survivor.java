package com.github.Escipion48.Domain;

import java.util.ArrayList;
import java.util.Objects;

public class Survivor {

    private byte id;
    private Position position;
    private ArrayList<Item> items;

    public Survivor(byte id, Position position, ArrayList<Item> items) {

        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(items, "items must not be null");

        this.id = id;
        this.position = position;
        this.items = items;
    }
}
