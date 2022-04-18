package com.vicras.abaclib.engine.pip.reducer.impl;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.pip.reducer.AttributeValuesReducer;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class RandomAttributeValuesReducer implements AttributeValuesReducer {
    @Override
    public <T> T reduceResult(Collection<T> attributeValues, Attribute attribute) {
        if (attributeValues.isEmpty()) {
            throw new AttributeNotFoundException(attribute);
        }
        if (attributeValues.size() > 1) {
            log.info(
                    "PIP found more then one value for attribute {}, random value was taken",
                    attribute.toString());
        }
        return attributeValues.stream().findFirst().get();
    }
}
