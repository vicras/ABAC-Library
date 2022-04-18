package com.vicras.abaclib.engine.model.attribute.impl;

import com.vicras.abaclib.engine.model.attribute.StringAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class StringAttributeImpl<T> implements StringAttribute<T> {
    private String name;
    private Class<T> attributeClass;
}
