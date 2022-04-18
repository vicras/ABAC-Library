package com.vicras.abaclib.engine.model.exception;

import static java.lang.String.format;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.exception.AbacException;

import java.util.Collection;

public class TooManyArgumentsException extends AbacException {
    public TooManyArgumentsException(Attribute<?> attribute, Collection<?> attributeValues) {
        super(format(
                "Find %s values for attribute %s can't reduce it to one.",
                attributeValues.size(),
                attribute.toString())
        );
    }
}
