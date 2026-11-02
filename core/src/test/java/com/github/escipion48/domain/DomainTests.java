package com.github.escipion48.domain;

import com.github.escipion48.domain.items.Item;
import com.github.escipion48.domain.items.Supply;
import com.github.escipion48.domain.items.Weapon;
import com.github.escipion48.domain.rules.*;

import java.util.List;
import java.util.function.Predicate;

public final class DomainTests {

    private static int checks;

    private static final Weapon SWORD =
        new Weapon("Sword", 5, 1);

    private static final Supply MEDKIT =
        new Supply("Medkit", 10);

    public static void main(String[] args) {
        invalidRules();
        reproducibility();
        invalidActionsAreAtomic();
        inventoryAndCombat();
        zombieRounds();
        healingRevivalAndVictory();
        defeatStopsTheTurn();
        skippedSpawns();
        delayedReinforcements();
        minimumSpawnDistance();
        randomGeneration();
        nearestTargetAndDiagonalStep();
        immutableViews();
        initialVictory();

        System.out.println("OK: " + checks + " checks");
    }

    private static void check(boolean condition, String message) {
        checks++;

        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void expect(
        Class<? extends Throwable> type,
        Runnable action
    ) {
        checks++;

        try {
            action.run();
        } catch (Throwable error) {
            if (type.isInstance(error)) {
                return;
            }

            throw new AssertionError(
                "Expected " + type.getName(),
                error
            );
        }

        throw new AssertionError(
            "Expected exception: " + type.getName());
    }

    private static void rejectsWithoutChanges(
        Game game,
        Runnable action
    ) {
        GameSnapshot before = game.getSnapshot();

        expect(RuntimeException.class, action);

        check(
            before.equals(game.getSnapshot()),
            "rejected action modified game state"
        );
    }

    private static SurvivorRules survivors(
        int health,
        int maximumHealth,
        int actions,
        int count,
        int capacity,
        List<Item> items
    ) {
        return new SurvivorRules(
            health,
            maximumHealth,
            actions,
            capacity,
            IntRange.fixed(count),
            PositionRange.fixed(new Position(0, 0)),
            items
        );
    }

    private static ZombieRules zombies(
        int health,
        int actions,
        int damage,
        int initial,
        int perTurn,
        int distance
    ) {
        return new ZombieRules(
            IntRange.fixed(health),
            IntRange.fixed(actions),
            IntRange.fixed(damage),
            IntRange.fixed(initial),
            IntRange.fixed(perTurn),
            distance
        );
    }

    private static GameRules rules(
        SurvivorRules survivors,
        ZombieRules zombies
    ) {
        return new GameRules(
            survivors,
            zombies,
            new MapRules(3, 1),
            PositionRange.fixed(new Position(2, 0))
        );
    }

    private static Game findGame(
        GameRules rules,
        Predicate<GameSnapshot> condition
    ) {
        for (long seed = 0; seed < 10_000; seed++) {
            Game game = Game.create(rules, seed);

            if (condition.test(game.getSnapshot())) {
                return game;
            }
        }

        throw new AssertionError(
            "could not find a matching deterministic scenario");
    }

    private static GameSnapshot.SurvivorView survivor(
        GameSnapshot snapshot,
        long id
    ) {
        return snapshot.survivors()
            .stream()
            .filter(s -> s.id() == id)
            .findFirst()
            .orElseThrow();
    }

    private static List<GameEvent.Attacked> zombieAttacks(
        GameResult result
    ) {
        return result.events()
            .stream()
            .filter(GameEvent.Attacked.class::isInstance)
            .map(GameEvent.Attacked.class::cast)
            .filter(e ->
                e.attackerKind()
                    == GameEvent.EntityKind.ZOMBIE)
            .toList();
    }

    private static void invalidRules() {
        expect(IllegalArgumentException.class,
            () -> new IntRange(2, 1));

        expect(IllegalArgumentException.class,
            () -> new MapRules(0, 1));

        expect(IllegalArgumentException.class,
            () -> new ItemSpawnRules(Double.NaN, List.of()));

        expect(IllegalArgumentException.class,
            () -> new ItemSpawnRules(1, List.of()));

        expect(IllegalArgumentException.class,
            () -> survivors(10, 5, 1, 1, 0, List.of()));

        expect(IllegalArgumentException.class,
            () -> survivors(1, 1, 1, 0, 0, List.of()));

        expect(IllegalArgumentException.class,
            () -> survivors(1, 1, 1, 1, 0, List.of(SWORD)));

        expect(IllegalArgumentException.class,
            () -> zombies(1, 1, 0, 1, 0, 0));

        SurvivorRules s = survivors(
            10, 10, 2, 1, 0, List.of());

        ZombieRules z = zombies(
            5, 1, 1, 0, 0, 0);

        expect(IllegalArgumentException.class,
            () -> new GameRules(
                s,
                z,
                new MapRules(2, 2),
                PositionRange.fixed(new Position(2, 0))
            ));

        check(
            new Position(0, 0).distanceTo(new Position(2, 2)) == 2,
            "diagonal distance"
        );
    }

    private static void reproducibility() {
        GameRules rules = rules(
            survivors(100, 100, 3, 2, 0, List.of()),
            zombies(5, 2, 1, 2, 1, 0)
        );

        Game a = Game.create(rules, 123);
        Game b = Game.create(rules, 123);

        check(
            a.getInitialResult().equals(b.getInitialResult()),
            "same seed must produce same initialization"
        );

        for (int i = 0; i < 3; i++) {
            check(
                a.endTurn().equals(b.endTurn()),
                "same commands must produce same results"
            );
        }
    }

    private static void invalidActionsAreAtomic() {
        Game game = Game.create(
            rules(
                survivors(
                    10, 10, 2, 1, 2,
                    List.of(SWORD, MEDKIT)),
                zombies(5, 1, 1, 0, 0, 0)
            ),
            1
        );

        rejectsWithoutChanges(game,
            () -> game.moveSurvivor(1, null));

        rejectsWithoutChanges(game,
            () -> game.moveSurvivor(1, new Position(3, 0)));

        rejectsWithoutChanges(game,
            () -> game.moveSurvivor(1, new Position(0, 0)));

        rejectsWithoutChanges(game,
            () -> game.moveSurvivor(999, new Position(1, 0)));

        rejectsWithoutChanges(game,
            () -> game.consumeSupply(1, 0));

        rejectsWithoutChanges(game,
            () -> game.consumeSupply(1, 1));

        rejectsWithoutChanges(game,
            () -> game.dropItem(1, -1));

        rejectsWithoutChanges(game,
            () -> game.pickUpItem(1));

        Game fullMap = Game.create(
            new GameRules(
                survivors(
                    10, 10, 2, 1, 1,
                    List.of(SWORD)),
                zombies(5, 1, 1, 0, 0, 0),
                new MapRules(
                    3, 1,
                    new ItemSpawnRules(1, List.of(MEDKIT))),
                PositionRange.fixed(new Position(2, 0))
            ),
            1
        );

        rejectsWithoutChanges(fullMap,
            () -> fullMap.pickUpItem(1));

        rejectsWithoutChanges(fullMap,
            () -> fullMap.dropItem(1, 0));
    }

    private static void inventoryAndCombat() {
        Game game = findGame(
            rules(
                survivors(
                    10, 10, 3, 1, 2,
                    List.of(SWORD, MEDKIT)),
                zombies(5, 1, 1, 1, 0, 0)
            ),
            state -> state.zombies().get(0).position()
                .equals(new Position(0, 0))
        );

        game.attackZombie(1, 1, 0);

        check(
            game.getSnapshot().zombies().get(0).health() == 0,
            "attack in the same cell"
        );

        check(
            survivor(game.getSnapshot(), 1).actionsRemaining() == 2,
            "attack costs one action"
        );

        rejectsWithoutChanges(game,
            () -> game.attackZombie(1, 1, 0));

        game.dropItem(1, 1);

        check(
            game.getSnapshot().mapItems()
                .get(new Position(0, 0)).equals(MEDKIT),
            "dropped item must be on the map"
        );

        game.pickUpItem(1);

        check(
            survivor(game.getSnapshot(), 1).items()
                .equals(List.of(SWORD, MEDKIT)),
            "picked item must return to inventory"
        );

        check(
            game.getSnapshot().mapItems().isEmpty(),
            "picked item must leave the map"
        );

        check(
            survivor(game.getSnapshot(), 1).actionsRemaining() == 0,
            "all three actions must be spent"
        );

        rejectsWithoutChanges(game,
            () -> game.dropItem(1, 1));

        Game distant = findGame(
            rules(
                survivors(
                    10, 10, 2, 1, 1,
                    List.of(SWORD)),
                zombies(5, 1, 1, 1, 0, 0)
            ),
            state -> state.zombies().get(0).position()
                .equals(new Position(2, 0))
        );

        rejectsWithoutChanges(distant,
            () -> distant.attackZombie(1, 1, 0));
    }

    private static void zombieRounds() {
        Game game = findGame(
            rules(
                survivors(20, 20, 3, 1, 0, List.of()),
                zombies(5, 2, 2, 2, 0, 0)
            ),
            state -> state.zombies().stream().allMatch(z ->
                z.position().equals(new Position(0, 0)))
        );

        GameResult result = game.endTurn();

        List<Long> order = zombieAttacks(result)
            .stream()
            .map(GameEvent.Attacked::attackerId)
            .toList();

        check(
            order.equals(List.of(1L, 2L, 1L, 2L)),
            "zombies must act in rounds, ordered by id"
        );

        check(
            survivor(result.state(), 1).health() == 12,
            "four attacks of two damage"
        );

        check(
            survivor(result.state(), 1).actionsRemaining() == 3,
            "survivor actions must be restored"
        );

        check(
            result.state().turn() == 2,
            "next survivor turn must start"
        );
    }

    private static void healingRevivalAndVictory() {
        Game game = findGame(
            rules(
                survivors(
                    2, 2, 3, 2, 2,
                    List.of(SWORD, MEDKIT)),
                zombies(5, 1, 2, 1, 0, 0)
            ),
            state -> state.zombies().get(0).position()
                .equals(new Position(0, 0))
        );

        game.endTurn();

        check(
            !survivor(game.getSnapshot(), 1).isAlive(),
            "lowest-id survivor must be selected in a tie"
        );

        check(
            survivor(game.getSnapshot(), 2).isAlive(),
            "second survivor must remain alive"
        );

        game.attackZombie(2, 1, 0);

        GameResult healed = game.healSurvivor(2, 1, 1);

        check(
            survivor(healed.state(), 1).isAlive(),
            "dead survivor must be revived"
        );

        check(
            survivor(healed.state(), 1).actionsRemaining() == 0,
            "revival must not restore actions immediately"
        );

        check(
            survivor(healed.state(), 2).actionsRemaining() == 1,
            "healer pays attack and healing actions"
        );

        check(
            survivor(healed.state(), 2).items().equals(List.of(SWORD)),
            "healer's supply must be consumed"
        );

        rejectsWithoutChanges(game,
            () -> game.moveSurvivor(1, new Position(1, 0)));

        game.endTurn();

        check(
            survivor(game.getSnapshot(), 1).actionsRemaining() == 3,
            "revived survivor acts on next turn"
        );

        game.moveSurvivor(1, new Position(1, 0));
        game.moveSurvivor(1, new Position(2, 0));

        check(
            game.getSnapshot().status() == Game.Status.ACTIVE,
            "one survivor at exit is not sufficient"
        );

        game.moveSurvivor(2, new Position(1, 0));
        game.moveSurvivor(2, new Position(2, 0));

        check(
            game.getSnapshot().status() == Game.Status.WON,
            "all living survivors at exit must win"
        );

        rejectsWithoutChanges(game, game::endTurn);
    }

    private static void defeatStopsTheTurn() {
        Game game = findGame(
            rules(
                survivors(2, 2, 3, 2, 0, List.of()),
                zombies(5, 2, 2, 1, 4, 0)
            ),
            state -> state.zombies().get(0).position()
                .equals(new Position(0, 0))
        );

        GameResult result = game.endTurn();

        List<Long> targets = zombieAttacks(result)
            .stream()
            .map(GameEvent.Attacked::targetId)
            .toList();

        check(
            targets.equals(List.of(1L, 2L)),
            "zombie must retarget after killing"
        );

        check(
            result.state().status() == Game.Status.LOST,
            "all dead must mean defeat"
        );

        check(
            result.state().zombies().size() == 1,
            "no reinforcements after defeat"
        );

        check(
            result.state().turn() == 1,
            "defeat must not start a new turn"
        );

        rejectsWithoutChanges(game, game::endTurn);
    }

    private static void skippedSpawns() {
        Game game = Game.create(
            new GameRules(
                survivors(10, 10, 2, 1, 0, List.of()),
                zombies(5, 1, 1, 3, 2, 2),
                new MapRules(2, 1),
                PositionRange.fixed(new Position(1, 0))
            ),
            1
        );

        check(
            game.getSnapshot().zombies().isEmpty(),
            "impossible initial spawns must be skipped"
        );

        check(
            game.getInitialResult().events().stream()
                .anyMatch(e ->
                    e instanceof GameEvent.SpawnSkipped skipped
                        && skipped.count() == 3),
            "initial skipped count must be reported"
        );

        GameResult result = game.endTurn();

        check(
            result.events().stream().anyMatch(e ->
                e instanceof GameEvent.SpawnSkipped skipped
                    && skipped.count() == 2),
            "reinforcement skipped count must be reported"
        );
    }

    private static void delayedReinforcements() {
        Game game = Game.create(
            rules(
                survivors(10, 10, 2, 1, 0, List.of()),
                zombies(5, 2, 1, 0, 1, 0)
            ),
            1
        );

        game.endTurn();

        check(
            survivor(game.getSnapshot(), 1).health() == 10,
            "new zombies must not attack immediately"
        );

        check(
            game.getSnapshot().zombies().size() == 1
                && game.getSnapshot().zombies().get(0)
                .actionsRemaining() == 0,
            "new zombie starts with no available actions"
        );

        game.endTurn();

        check(
            survivor(game.getSnapshot(), 1).health() < 10,
            "existing zombie must act in the next phase"
        );

        check(
            game.getSnapshot().zombies().size() == 2,
            "another reinforcement must appear"
        );
    }

    private static void minimumSpawnDistance() {
        SurvivorRules s = new SurvivorRules(
            10, 10, 2, 0,
            IntRange.fixed(1),
            PositionRange.fixed(new Position(2, 2)),
            List.of()
        );

        Game game = Game.create(
            new GameRules(
                s,
                zombies(5, 1, 1, 5, 0, 2),
                new MapRules(5, 5),
                PositionRange.fixed(new Position(4, 4))
            ),
            1
        );

        check(
            game.getSnapshot().zombies().size() == 5,
            "all requested zombies should fit"
        );

        for (GameSnapshot.ZombieView zombie
            : game.getSnapshot().zombies()) {

            check(
                zombie.position().distanceTo(new Position(2, 2)) >= 2,
                "spawn must respect minimum distance"
            );
        }
    }

    private static void randomGeneration() {
        GameConfig config = new GameConfig(
            new SurvivorRules(
                10, 20, 3, 0,
                new IntRange(1, 3),
                new PositionRange(
                    new IntRange(0, 1),
                    new IntRange(0, 1)),
                List.of()
            ),
            new ZombieRules(
                new IntRange(5, 10),
                new IntRange(1, 3),
                new IntRange(1, 4),
                new IntRange(1, 4),
                new IntRange(0, 2),
                0
            ),
            new IntRange(5, 7),
            new IntRange(5, 7),
            ItemSpawnRules.none(),
            new PositionRange(
                new IntRange(3, 4),
                new IntRange(3, 4))
        );

        for (long seed = 0; seed < 20; seed++) {
            Game game = Game.create(config, seed);
            GameSnapshot state = game.getSnapshot();

            check(
                state.rules().map().width() >= 5
                    && state.rules().map().width() <= 7,
                "generated width"
            );

            check(
                state.rules().map().height() >= 5
                    && state.rules().map().height() <= 7,
                "generated height"
            );

            check(
                state.survivors().size() >= 1
                    && state.survivors().size() <= 3,
                "generated survivor count"
            );

            check(
                state.zombies().size() >= 1
                    && state.zombies().size() <= 4,
                "generated zombie count"
            );

            for (GameSnapshot.ZombieView zombie : state.zombies()) {
                check(
                    zombie.health() >= 5 && zombie.health() <= 10,
                    "generated zombie health"
                );

                check(
                    zombie.damage() >= 1 && zombie.damage() <= 4,
                    "generated zombie damage"
                );

                check(
                    zombie.actionsPerTurn() >= 1
                        && zombie.actionsPerTurn() <= 3,
                    "generated zombie action capacity"
                );

                check(
                    state.rules().map().contains(zombie.position()),
                    "spawn inside resolved map"
                );
            }

            Game replay = Game.create(game.getRules(), game.getSeed());

            check(
                state.equals(replay.getSnapshot()),
                "resolved rules plus seed reproduce initial state"
            );
        }
    }

    private static void nearestTargetAndDiagonalStep() {
        MapRules map = new MapRules(3, 3);

        SurvivorRules rules = survivors(
            10, 10, 2, 1, 0, List.of());

        Survivor first = new Survivor(
            1, rules, map, new Position(0, 0));

        Survivor second = new Survivor(
            2, rules, map, new Position(2, 0));

        Zombie zombie = new Zombie(
            1, map, new Position(1, 2), 5, 2, 1);

        check(
            ZombieAI.nearest(zombie, List.of(second, first)).id() == 1,
            "AI tie-break must not depend on collection order"
        );

        check(
            ZombieAI.nextStep(
                zombie.position(),
                first.position()
            ).equals(new Position(0, 1)),
            "AI should take a diagonal step"
        );
    }

    private static void immutableViews() {
        Game game = Game.create(
            rules(
                survivors(
                    10, 10, 2, 1, 1,
                    List.of(SWORD)),
                zombies(5, 1, 1, 0, 0, 0)
            ),
            1
        );

        GameSnapshot old = game.getSnapshot();

        expect(UnsupportedOperationException.class,
            () -> old.survivors().clear());

        expect(UnsupportedOperationException.class,
            () -> old.survivors().get(0).items().clear());

        expect(UnsupportedOperationException.class,
            () -> old.mapItems().put(new Position(0, 0), MEDKIT));

        expect(UnsupportedOperationException.class,
            () -> game.getInitialResult().events().clear());

        game.moveSurvivor(1, new Position(1, 0));

        check(
            old.survivors().get(0).position()
                .equals(new Position(0, 0)),
            "previous snapshot must not change"
        );
    }

    private static void initialVictory() {
        Game game = Game.create(
            new GameRules(
                survivors(10, 10, 2, 2, 0, List.of()),
                zombies(5, 1, 1, 0, 0, 0),
                new MapRules(1, 1),
                PositionRange.fixed(new Position(0, 0))
            ),
            1
        );

        check(
            game.getSnapshot().status() == Game.Status.WON,
            "all survivors initially at exit must win"
        );

        rejectsWithoutChanges(game, game::endTurn);
    }
}
