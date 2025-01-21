package com.aniwhere.aniwhereapi.exception.advice;

public class NoAdultNoAccessException extends RuntimeException {
    public NoAdultNoAccessException(String message) {
        super(message);
    }
}
