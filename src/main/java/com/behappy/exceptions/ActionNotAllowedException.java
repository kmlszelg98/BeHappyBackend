package com.behappy.exceptions;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException() {
        super();
    }
    public ActionNotAllowedException(String s) {
        super(s);
    }
}
