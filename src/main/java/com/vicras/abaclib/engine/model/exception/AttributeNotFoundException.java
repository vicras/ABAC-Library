package com.vicras.abaclib.engine.model.exception;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.exception.AbacException;

public class AttributeNotFoundException extends AbacException {
    public AttributeNotFoundException(Attribute<?> attribute) {
        super("Attribute not found:\n" + attribute.toString());
    }
}
