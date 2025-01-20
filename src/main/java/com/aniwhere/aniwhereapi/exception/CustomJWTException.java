package com.aniwhere.aniwhereapi.exception;

public class CustomJWTException extends RuntimeException {

    public CustomJWTException(String message) {
        super(message);
    }
}
