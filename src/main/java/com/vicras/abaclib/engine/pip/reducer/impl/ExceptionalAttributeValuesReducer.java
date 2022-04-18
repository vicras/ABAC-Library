package com.vicras.abaclib.engine.pip.reducer.impl;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.TooManyArgumentsException;
import com.vicras.abaclib.engine.pip.reducer.AttributeValuesReducer;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public class ExceptionalAttributeValuesReducer implements AttributeValuesReducer {
    private final static int ONE_ELEMENT = 1;

    @Override
    public <T> T reduceResult(Collection<T> attributeValues, Attribute attribute) {
        if (attributeValues.isEmpty()) {
            throw new AttributeNotFoundException(attribute);
        }
        if (attributeValues.size() != ONE_ELEMENT) {
            throw new TooManyArgumentsException(attribute, attributeValues);
        }
        return attributeValues.stream().findFirst().get();
    }
}
