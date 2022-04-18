package com.vicras.abaclib.engine.model.attribute.provider;

import com.vicras.abaclib.engine.model.attribute.Attribute;

public interface AttributeProvider {
    <T> T getAttribute(Attribute<T> attribute);
}
