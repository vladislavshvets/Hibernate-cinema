package com.dev.cinema.exception;

public class NotFoundInjectorClassException extends RuntimeException {
    public NotFoundInjectorClassException(String message, Exception e) {
        super(message, e);
    }
}
