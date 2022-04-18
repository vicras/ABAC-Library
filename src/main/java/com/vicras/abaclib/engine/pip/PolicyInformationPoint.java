package com.vicras.abaclib.engine.pip;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.context.ExecutionContext;

import java.util.Collection;

public interface PolicyInformationPoint {
    <T> T get(Attribute<T> attribute);

    <T> Collection<T> gets(Attribute<T> attribute);

    // bad solution. Will not work with async
    void setExecutionContext(ExecutionContext context);
}
