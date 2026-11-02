package com.github.escipion48.domain;

import java.util.Collection;
import java.util.Objects;

final class ZombieAI {

    private ZombieAI() {
    }

    static Survivor nearest(
        Zombie zombie,
        Collection<Survivor> survivors
    ) {
        Objects.requireNonNull(zombie, "zombie");
        Objects.requireNonNull(survivors, "survivors");

        Survivor best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Survivor survivor : survivors) {
            Objects.requireNonNull(survivor, "survivor");

            if (!survivor.isAlive()) {
                continue;
            }

            int distance = zombie.position()
                .distanceTo(survivor.position());

            if (best == null
                || distance < bestDistance
                || (distance == bestDistance
                && survivor.id() < best.id())) {

                best = survivor;
                bestDistance = distance;
            }
        }

        return best;
    }

    static Position nextStep(Position from, Position target) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(target, "target");

        if (from.equals(target)) {
            throw new IllegalArgumentException(
                "cannot approach the current position");
        }

        return new Position(
            from.x() + Integer.compare(target.x(), from.x()),
            from.y() + Integer.compare(target.y(), from.y())
        );
    }
}
