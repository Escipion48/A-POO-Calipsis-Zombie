package com.github.escipion48.domain;

import com.github.escipion48.domain.GameEvent.*;
import com.github.escipion48.domain.exceptions.GameRuleViolationException;
import com.github.escipion48.domain.exceptions.MaxHealthException;
import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.items.Supply;
import com.github.escipion48.domain.items.Weapon;
import com.github.escipion48.domain.rules.GameConfig;
import com.github.escipion48.domain.rules.GameRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.random.RandomGenerator;

public final class Game {

    public enum Status {
        ACTIVE,
        WON,
        LOST
    }

    public enum Phase {
        SURVIVORS,
        ZOMBIES,
        FINISHED
    }

    private final GameRules rules;
    private final long seed;
    private final RandomGenerator random;

    private final GameMap map;
    private final Position exit;

    // TreeMap mantiene el procesamiento ordenado por identificador.
    private final Map<Long, Survivor> survivors = new TreeMap<>();
    private final Map<Long, Zombie> zombies = new TreeMap<>();

    private final GameResult initialResult;

    private Status status = Status.ACTIVE;
    private Phase phase = Phase.SURVIVORS;

    private long turn = 1;
    private long revision = 0;
    private long nextZombieId = 1;

    private Game(GameRules rules, long seed) {
        this.rules = Objects.requireNonNull(rules, "rules");
        this.seed = seed;
        this.random = new Random(seed);

        this.map = GameMap.createNewMap(rules.map(), random);
        this.exit = rules.exitPosition().sample(random);

        List<GameEvent> events = new ArrayList<>();
        events.add(new Started(turn, seed, exit));

        int survivorCount = rules.survivors()
            .initialCount()
            .sample(random);

        for (int i = 0; i < survivorCount; i++) {
            long id = (long) i + 1;

            Position position = rules.survivors()
                .initialPosition()
                .sample(random);

            Survivor survivor = new Survivor(
                id,
                rules.survivors(),
                rules.map(),
                position
            );

            survivors.put(id, survivor);
            events.add(new SurvivorSpawned(turn, viewOf(survivor)));
        }

        int initialZombies = rules.zombies()
            .initialCount()
            .sample(random);

        spawnZombies(initialZombies, events);
        checkEndOfGame(events);

        initialResult = new GameResult(events, snapshot());
    }

    public static Game create(GameRules rules, long seed) {
        return new Game(
            Objects.requireNonNull(rules, "rules"),
            seed
        );
    }

    public static Game create(GameConfig config, long seed) {
        Objects.requireNonNull(config, "config");

        // La generación de dimensiones usa su propio generador.
        // El estado de la partida comienza después con la semilla original.
        GameRules resolved = config.resolve(new Random(seed));

        return create(resolved, seed);
    }

    public GameRules getRules() {
        return rules;
    }

    public long getSeed() {
        return seed;
    }

    public GameResult getInitialResult() {
        return initialResult;
    }

    public synchronized GameSnapshot getSnapshot() {
        return snapshot();
    }

    public synchronized GameResult moveSurvivor(
        long survivorId,
        Position destination
    ) {
        requireSurvivorTurn();

        Survivor survivor = survivor(survivorId);
        Position previous = survivor.position();

        // Valida estado, acciones, límites y adyacencia antes de cambiar nada.
        survivor.move(destination);

        List<GameEvent> events = new ArrayList<>();
        events.add(new Moved(
            turn,
            EntityKind.SURVIVOR,
            survivor.id(),
            previous,
            survivor.position()
        ));

        return complete(events);
    }

    public synchronized GameResult attackZombie(
        long survivorId,
        long zombieId,
        int weaponIndex
    ) {
        requireSurvivorTurn();

        Survivor survivor = survivor(survivorId);
        survivor.requireCanAct();

        Item item = survivor.itemAt(weaponIndex);

        if (!(item instanceof Weapon weapon)) {
            throw new GameRuleViolationException(
                "selected inventory item is not a weapon");
        }

        Zombie zombie = zombie(zombieId);

        if (!zombie.isAlive()) {
            throw new GameRuleViolationException(
                "cannot attack a dead zombie");
        }

        if (survivor.position().distanceTo(zombie.position())
            > weapon.range()) {

            throw new GameRuleViolationException(
                "zombie is outside weapon range");
        }

        // Todas las precondiciones están comprobadas.
        int previousHealth = zombie.health();

        zombie.takeDamage(weapon.damage());
        survivor.spendAction();

        List<GameEvent> events = new ArrayList<>();
        events.add(new Attacked(
            turn,
            EntityKind.SURVIVOR,
            survivor.id(),
            EntityKind.ZOMBIE,
            zombie.id(),
            previousHealth - zombie.health(),
            zombie.health()
        ));

        if (!zombie.isAlive()) {
            events.add(new Died(
                turn,
                EntityKind.ZOMBIE,
                zombie.id()
            ));
        }

        return complete(events);
    }

    public synchronized GameResult consumeSupply(
        long survivorId,
        int supplyIndex
    ) {
        return healSurvivor(
            survivorId,
            survivorId,
            supplyIndex
        );
    }

    public synchronized GameResult healSurvivor(
        long healerId,
        long targetId,
        int supplyIndex
    ) {
        requireSurvivorTurn();

        Survivor healer = survivor(healerId);
        healer.requireCanAct();

        Survivor target = survivor(targetId);
        Item item = healer.itemAt(supplyIndex);

        if (!(item instanceof Supply supply)) {
            throw new GameRuleViolationException(
                "selected inventory item is not a supply");
        }

        if (healer.position().distanceTo(target.position()) > 1) {
            throw new GameRuleViolationException(
                "target must be in the same or an adjacent position");
        }

        if (target.health() == rules.survivors().maxHealth()) {
            throw new MaxHealthException();
        }

        boolean revived = !target.isAlive();

        int restored = target.heal(supply.healing());

        healer.removeItem(supplyIndex);
        healer.spendAction();

        List<GameEvent> events = new ArrayList<>();
        events.add(new Healed(
            turn,
            healer.id(),
            target.id(),
            supply,
            restored,
            target.health(),
            revived
        ));

        return complete(events);
    }

    public synchronized GameResult pickUpItem(long survivorId) {
        requireSurvivorTurn();

        Survivor survivor = survivor(survivorId);
        survivor.requireCanAct();
        survivor.requireInventorySpace();

        Position position = survivor.position();

        Item item = map.getItemAt(position)
            .orElseThrow(() -> new GameRuleViolationException(
                "position does not contain an item"));

        int inventoryIndex = survivor.items().size();

        // Comprobados espacio, posición, existencia y acciones.
        map.removeItemAt(position);
        survivor.addItem(item);
        survivor.spendAction();

        List<GameEvent> events = new ArrayList<>();
        events.add(new ItemTransferred(
            turn,
            survivor.id(),
            item,
            position,
            inventoryIndex,
            Transfer.PICKED_UP
        ));

        return complete(events);
    }

    public synchronized GameResult dropItem(
        long survivorId,
        int inventoryIndex
    ) {
        requireSurvivorTurn();

        Survivor survivor = survivor(survivorId);
        survivor.requireCanAct();

        Item item = survivor.itemAt(inventoryIndex);
        Position position = survivor.position();

        if (map.hasItemAt(position)) {
            throw new GameRuleViolationException(
                "position already contains an item");
        }

        map.placeItem(position, item);
        survivor.removeItem(inventoryIndex);
        survivor.spendAction();

        List<GameEvent> events = new ArrayList<>();
        events.add(new ItemTransferred(
            turn,
            survivor.id(),
            item,
            position,
            inventoryIndex,
            Transfer.DROPPED
        ));

        return complete(events);
    }

    public synchronized GameResult endTurn() {
        requireSurvivorTurn();

        if (turn == Long.MAX_VALUE) {
            throw new IllegalStateException(
                "turn counter exhausted");
        }

        if (nextZombieId
            > Long.MAX_VALUE - rules.zombies().perTurnCount().max()) {

            throw new IllegalStateException(
                "zombie identifier space exhausted");
        }

        List<GameEvent> events = new ArrayList<>();

        phase = Phase.ZOMBIES;
        events.add(new PhaseChanged(turn, phase));

        for (Zombie zombie : zombies.values()) {
            zombie.startTurn();
        }

        // Una acción por zombi en cada ronda, en orden de identificador.
        boolean acted;

        do {
            acted = false;

            for (Zombie zombie : zombies.values()) {
                if (status != Status.ACTIVE) {
                    break;
                }

                if (!zombie.isAlive()
                    || zombie.actionsRemaining() == 0) {
                    continue;
                }

                Survivor target = ZombieAI.nearest(
                    zombie,
                    survivors.values()
                );

                if (target == null) {
                    checkEndOfGame(events);
                    break;
                }

                performZombieAction(zombie, target, events);
                acted = true;

                checkEndOfGame(events);
            }
        } while (acted && status == Status.ACTIVE);

        // Una derrota interrumpe la fase: no aparecen refuerzos
        // ni se restauran las acciones.
        if (status != Status.ACTIVE) {
            return complete(events);
        }

        int reinforcements = rules.zombies()
            .perTurnCount()
            .sample(random);

        spawnZombies(reinforcements, events);

        turn++;

        for (Survivor survivor : survivors.values()) {
            survivor.startTurn();
        }

        phase = Phase.SURVIVORS;
        events.add(new PhaseChanged(turn, phase));

        return complete(events);
    }

    private void performZombieAction(
        Zombie zombie,
        Survivor target,
        List<GameEvent> events
    ) {
        int distance = zombie.position()
            .distanceTo(target.position());

        if (distance <= 1) {
            int previousHealth = target.health();

            zombie.spendAction();
            target.takeDamage(zombie.damage());

            events.add(new Attacked(
                turn,
                EntityKind.ZOMBIE,
                zombie.id(),
                EntityKind.SURVIVOR,
                target.id(),
                previousHealth - target.health(),
                target.health()
            ));

            if (!target.isAlive()) {
                events.add(new Died(
                    turn,
                    EntityKind.SURVIVOR,
                    target.id()
                ));
            }

            return;
        }

        Position previous = zombie.position();

        Position destination = ZombieAI.nextStep(
            previous,
            target.position()
        );

        zombie.move(destination);

        events.add(new Moved(
            turn,
            EntityKind.ZOMBIE,
            zombie.id(),
            previous,
            destination
        ));
    }

    private void spawnZombies(
        int count,
        List<GameEvent> events
    ) {
        if (count < 0) {
            throw new IllegalArgumentException(
                "spawn count must not be negative");
        }

        Objects.requireNonNull(events, "events");

        for (int i = 0; i < count; i++) {
            Position position = findSpawnPosition();

            if (position == null) {
                // Compartir casilla está permitido. Si no hay sitio
                // para uno, tampoco lo hay para los restantes.
                events.add(new SpawnSkipped(turn, count - i));
                return;
            }

            Zombie zombie = Zombie.create(
                nextZombieId,
                rules.map(),
                rules.zombies(),
                random,
                position
            );

            zombies.put(zombie.id(), zombie);
            nextZombieId++;

            events.add(new ZombieSpawned(turn, viewOf(zombie)));
        }
    }

    private Position findSpawnPosition() {
        int minimumDistance = rules.zombies()
            .minimumSpawnDistance();

        if (minimumDistance == 0) {
            return rules.map().randomPosition(random);
        }

        // Selección uniforme entre posiciones válidas sin almacenar
        // una lista de todas ellas ni usar intentos aleatorios ilimitados.
        Position selected = null;
        long eligibleCount = 0;

        for (int x = 0; x < rules.map().width(); x++) {
            for (int y = 0; y < rules.map().height(); y++) {
                Position candidate = new Position(x, y);

                if (!validSpawn(candidate, minimumDistance)) {
                    continue;
                }

                eligibleCount++;

                if (random.nextLong(eligibleCount) == 0) {
                    selected = candidate;
                }
            }
        }

        return selected;
    }

    private boolean validSpawn(
        Position candidate,
        int minimumDistance
    ) {
        for (Survivor survivor : survivors.values()) {
            if (survivor.isAlive()
                && candidate.distanceTo(survivor.position())
                < minimumDistance) {

                return false;
            }
        }

        return true;
    }

    private Survivor survivor(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "survivor id must be positive");
        }

        Survivor survivor = survivors.get(id);

        if (survivor == null) {
            throw new IllegalArgumentException(
                "unknown survivor id: " + id);
        }

        return survivor;
    }

    private Zombie zombie(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "zombie id must be positive");
        }

        Zombie zombie = zombies.get(id);

        if (zombie == null) {
            throw new IllegalArgumentException(
                "unknown zombie id: " + id);
        }

        return zombie;
    }

    private void requireSurvivorTurn() {
        if (status != Status.ACTIVE) {
            throw new GameRuleViolationException(
                "game has already finished");
        }

        if (phase != Phase.SURVIVORS) {
            throw new GameRuleViolationException(
                "it is not the survivors' turn");
        }

        if (revision == Long.MAX_VALUE) {
            throw new IllegalStateException(
                "revision counter exhausted");
        }
    }

    private void checkEndOfGame(List<GameEvent> events) {
        if (status != Status.ACTIVE) {
            return;
        }

        boolean anyAlive = survivors.values()
            .stream()
            .anyMatch(Survivor::isAlive);

        boolean allAtExit = survivors.values()
            .stream()
            .allMatch(survivor ->
                survivor.isAlive()
                    && survivor.position().equals(exit));

        if (!anyAlive) {
            status = Status.LOST;
        } else if (allAtExit) {
            status = Status.WON;
        } else {
            return;
        }

        phase = Phase.FINISHED;
        events.add(new Finished(turn, status));
    }

    private GameResult complete(List<GameEvent> events) {
        checkEndOfGame(events);
        revision++;

        return new GameResult(events, snapshot());
    }

    private GameSnapshot snapshot() {
        List<GameSnapshot.SurvivorView> survivorViews =
            survivors.values()
                .stream()
                .map(Game::viewOf)
                .toList();

        List<GameSnapshot.ZombieView> zombieViews =
            zombies.values()
                .stream()
                .map(Game::viewOf)
                .toList();

        return new GameSnapshot(
            rules,
            seed,
            turn,
            revision,
            status,
            phase,
            exit,
            map.getItems(),
            survivorViews,
            zombieViews
        );
    }

    private static GameSnapshot.SurvivorView viewOf(
        Survivor survivor
    ) {
        return new GameSnapshot.SurvivorView(
            survivor.id(),
            survivor.health(),
            survivor.actionsRemaining(),
            survivor.position(),
            survivor.items()
        );
    }

    private static GameSnapshot.ZombieView viewOf(
        Zombie zombie
    ) {
        return new GameSnapshot.ZombieView(
            zombie.id(),
            zombie.health(),
            zombie.actionsRemaining(),
            zombie.actionsPerTurn(),
            zombie.damage(),
            zombie.position()
        );
    }
}
