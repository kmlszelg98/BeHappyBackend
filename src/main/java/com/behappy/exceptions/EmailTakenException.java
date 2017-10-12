package com.behappy.exceptions;


public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String s) {
        super(s);
    }

    public EmailTakenException() {
        super();
    }
}
