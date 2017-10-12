package com.behappy.exceptions;


public class NoSuchTherapyException extends RuntimeException {
    public NoSuchTherapyException() {
        super();
    }
    public NoSuchTherapyException(String message) {
        super(message);
    }
}
