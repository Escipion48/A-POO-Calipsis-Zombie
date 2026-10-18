package com.github.escipion48.domain.exceptions;

public class SurvivorDeadException extends GameRuleViolationException {
    public SurvivorDeadException() {
        super("survivor is dead");
    }
}
