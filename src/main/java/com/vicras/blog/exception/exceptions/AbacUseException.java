package com.vicras.blog.exception.exceptions;

public abstract class AbacUseException extends RuntimeException {

    public AbacUseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AbacUseException(String msg) {
        super(msg);
    }
}