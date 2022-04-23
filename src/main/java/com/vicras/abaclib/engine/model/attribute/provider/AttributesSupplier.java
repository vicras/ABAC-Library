package com.vicras.abaclib.engine.model.attribute.provider;

import com.vicras.abaclib.engine.model.attribute.Attribute;

import java.util.Collection;

public interface AttributesSupplier {
    <T> Collection<T> getAttributes(Attribute<T> attribute) throws Exception;
}
