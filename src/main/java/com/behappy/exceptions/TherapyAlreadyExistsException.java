package com.behappy.exceptions;

public class TherapyAlreadyExistsException extends RuntimeException {
    public TherapyAlreadyExistsException() {
        super();
    }
    public TherapyAlreadyExistsException(String message) {
        super(message);
    }
}
