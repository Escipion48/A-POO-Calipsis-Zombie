package com.github.escipion48.domain;

import java.util.List;
import java.util.Objects;

public record GameResult(
    List<GameEvent> events,
    GameSnapshot state
) {

    public GameResult {
        events = List.copyOf(
            Objects.requireNonNull(events, "events"));

        Objects.requireNonNull(state, "state");
    }
}
