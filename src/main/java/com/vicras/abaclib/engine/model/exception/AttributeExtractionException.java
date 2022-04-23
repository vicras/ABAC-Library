package com.vicras.abaclib.engine.model.exception;

import com.vicras.abaclib.exception.AbacException;

public class AttributeExtractionException extends AbacException {
    public AttributeExtractionException(Throwable cause) {
        super("Exception during extraction process", cause);
    }
}
