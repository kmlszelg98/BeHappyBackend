package com.behappy.exceptions;


public class RoleNotAvailableException extends RuntimeException {
    public RoleNotAvailableException() {
        super();
    }
    public RoleNotAvailableException(String message) {
        super(message);
    }
}
