package com.behappy.exceptions;

public class NoSuchInvitationException extends RuntimeException {
    public NoSuchInvitationException(String s) {
        super(s);
    }
    public NoSuchInvitationException() {
        super();
    }
}
