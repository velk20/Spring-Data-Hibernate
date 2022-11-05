package com.example.springdataintro.exceptions;

public class EntityMissingException extends RuntimeException {
    public EntityMissingException(String message) {
        super(message);
    }
}
