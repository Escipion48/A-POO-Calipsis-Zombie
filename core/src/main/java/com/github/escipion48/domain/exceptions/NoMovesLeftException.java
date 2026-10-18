package com.github.escipion48.domain.exceptions;

public class NoMovesLeftException extends GameRuleViolationException {
    public NoMovesLeftException() {
        super("survivor has no moves left");
    }
}
