package com.vicras.abaclib.engine.model.attribute.provider;

import com.vicras.abaclib.engine.model.attribute.Attribute;

public interface AttributeSupplier {
    <T> T getAttribute(Attribute<T> attribute) throws Exception;
}
