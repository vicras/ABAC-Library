package com.vicras.blog.exception.exceptions.security;

import com.vicras.blog.exception.exceptions.AbacUseException;

public abstract class SecurityException extends AbacUseException {
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SecurityException(String msg) {
        super(msg);
    }
}
