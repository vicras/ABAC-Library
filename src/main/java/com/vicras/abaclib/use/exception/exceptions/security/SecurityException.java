package com.vicras.abaclib.use.exception.exceptions.security;

import com.vicras.abaclib.use.exception.exceptions.AbacUseException;

public abstract class SecurityException extends AbacUseException {
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SecurityException(String msg) {
        super(msg);
    }
}
