package com.github.escipion48.domain.exceptions;

public class InvalidMoveException extends GameRuleViolationException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
