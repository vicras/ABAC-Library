package com.vicras.abaclib.engine.model.attribute.provider;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.context.ExecutionContext;

import java.util.Collection;

public interface AttributeWithContextProvider {
    <T> Collection<T> getAttributes(Attribute<T> attribute, ExecutionContext context);
}
