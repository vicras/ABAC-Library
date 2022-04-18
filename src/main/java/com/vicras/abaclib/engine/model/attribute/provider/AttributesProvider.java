package com.vicras.abaclib.engine.model.attribute.provider;

import com.vicras.abaclib.engine.model.attribute.Attribute;

import java.util.Collection;

public interface AttributesProvider {
    <T> Collection<T> getAttributes(Attribute<T> attribute);
}
