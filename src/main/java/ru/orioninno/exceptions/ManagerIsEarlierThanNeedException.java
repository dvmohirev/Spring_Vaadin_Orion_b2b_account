package ru.orioninno.exceptions;

import lombok.Getter;

@Getter
public class ManagerIsEarlierThanNeedException extends RuntimeException {
    private final String message;

    public ManagerIsEarlierThanNeedException(String message) {
        this.message = message;
    }

}
