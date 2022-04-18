package com.vicras.abaclib.engine.pip.reducer;

import com.vicras.abaclib.engine.model.attribute.Attribute;

import java.util.Collection;

public interface AttributeValuesReducer {
    <T> T reduceResult(Collection<T> attributeValues, Attribute attribute);
}
