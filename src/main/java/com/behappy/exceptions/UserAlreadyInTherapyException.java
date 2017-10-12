package com.behappy.exceptions;


public class UserAlreadyInTherapyException extends RuntimeException {
    public UserAlreadyInTherapyException() {
        super();
    }
    public UserAlreadyInTherapyException(String message) {
        super(message);
    }
}
