package com.github.escipion48.domain.exceptions;

public class NoMovesLeftException extends GameRuleViolationException {

    public NoMovesLeftException() {
        super("actor has no actions left");
    }
}
