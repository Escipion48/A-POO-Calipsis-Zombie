package com.github.escipion48.domain;

import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.items.Supply;

import java.util.Objects;

public sealed interface GameEvent {

    long turn();

    enum EntityKind {
        SURVIVOR,
        ZOMBIE
    }

    enum Transfer {
        PICKED_UP,
        DROPPED
    }

    private static void requireTurn(long turn) {
        if (turn < 1) {
            throw new IllegalArgumentException(
                "turn must be positive");
        }
    }

    private static void requireId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "id must be positive");
        }
    }

    record Started(
        long turn,
        long seed,
        Position exit
    ) implements GameEvent {
        public Started {
            requireTurn(turn);
            Objects.requireNonNull(exit, "exit");
        }
    }

    record PhaseChanged(
        long turn,
        Game.Phase phase
    ) implements GameEvent {
        public PhaseChanged {
            requireTurn(turn);
            Objects.requireNonNull(phase, "phase");
        }
    }

    record SurvivorSpawned(
        long turn,
        GameSnapshot.SurvivorView survivor
    ) implements GameEvent {
        public SurvivorSpawned {
            requireTurn(turn);
            Objects.requireNonNull(survivor, "survivor");
        }
    }

    record ZombieSpawned(
        long turn,
        GameSnapshot.ZombieView zombie
    ) implements GameEvent {
        public ZombieSpawned {
            requireTurn(turn);
            Objects.requireNonNull(zombie, "zombie");
        }
    }

    record Moved(
        long turn,
        EntityKind kind,
        long id,
        Position from,
        Position to
    ) implements GameEvent {
        public Moved {
            requireTurn(turn);
            requireId(id);
            Objects.requireNonNull(kind, "kind");
            Objects.requireNonNull(from, "from");
            Objects.requireNonNull(to, "to");

            if (!from.isAdjacent(to)) {
                throw new IllegalArgumentException(
                    "movement event must describe an adjacent move");
            }
        }
    }

    record Attacked(
        long turn,
        EntityKind attackerKind,
        long attackerId,
        EntityKind targetKind,
        long targetId,
        int damage,
        int remainingHealth
    ) implements GameEvent {
        public Attacked {
            requireTurn(turn);
            requireId(attackerId);
            requireId(targetId);
            Objects.requireNonNull(attackerKind, "attackerKind");
            Objects.requireNonNull(targetKind, "targetKind");

            if (damage <= 0 || remainingHealth < 0) {
                throw new IllegalArgumentException(
                    "invalid attack event");
            }
        }
    }

    record Died(
        long turn,
        EntityKind kind,
        long id
    ) implements GameEvent {
        public Died {
            requireTurn(turn);
            requireId(id);
            Objects.requireNonNull(kind, "kind");
        }
    }

    record Healed(
        long turn,
        long healerId,
        long targetId,
        Supply supply,
        int restoredHealth,
        int remainingHealth,
        boolean revived
    ) implements GameEvent {
        public Healed {
            requireTurn(turn);
            requireId(healerId);
            requireId(targetId);
            Objects.requireNonNull(supply, "supply");

            if (restoredHealth <= 0 || remainingHealth <= 0) {
                throw new IllegalArgumentException(
                    "invalid healing event");
            }
        }
    }

    record ItemTransferred(
        long turn,
        long survivorId,
        Item item,
        Position position,
        int inventoryIndex,
        Transfer transfer
    ) implements GameEvent {
        public ItemTransferred {
            requireTurn(turn);
            requireId(survivorId);
            Objects.requireNonNull(item, "item");
            Objects.requireNonNull(position, "position");
            Objects.requireNonNull(transfer, "transfer");

            if (inventoryIndex < 0) {
                throw new IllegalArgumentException(
                    "inventory index must not be negative");
            }
        }
    }

    record SpawnSkipped(
        long turn,
        int count
    ) implements GameEvent {
        public SpawnSkipped {
            requireTurn(turn);

            if (count <= 0) {
                throw new IllegalArgumentException(
                    "skipped count must be positive");
            }
        }
    }

    record Finished(
        long turn,
        Game.Status status
    ) implements GameEvent {
        public Finished {
            requireTurn(turn);
            Objects.requireNonNull(status, "status");

            if (status == Game.Status.ACTIVE) {
                throw new IllegalArgumentException(
                    "finished event requires a terminal status");
            }
        }
    }
}
