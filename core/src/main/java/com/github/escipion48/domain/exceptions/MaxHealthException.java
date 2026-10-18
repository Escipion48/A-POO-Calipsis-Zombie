package com.github.escipion48.domain.exceptions;

public class MaxHealthException extends GameRuleViolationException {
    public MaxHealthException() {
        super("Survivor cannot heal when health is at maxed game ammount");
    }
}
