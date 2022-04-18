package com.vicras.abaclib.engine.model.exception;

import com.vicras.abaclib.exception.AbacException;

public class DuringAttributeExtractionException extends AbacException {
    public DuringAttributeExtractionException(Throwable cause) {
        super("Exception during extraction process", cause);
    }
}
