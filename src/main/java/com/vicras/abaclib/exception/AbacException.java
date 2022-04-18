package com.vicras.abaclib.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AbacException extends RuntimeException {
    public AbacException(String message) {
        super(message);
    }

    public AbacException(String message, Throwable cause) {
        super(message, cause);
    }
}
