package com.vicras.abaclib.engine.model.attribute.impl.tree;

import com.vicras.abaclib.engine.model.attribute.TreeAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class TreeAttributeImpl<T> implements TreeAttribute<T> {
    private static final char DELIMITER = '.';
    private String name;
    private Class<T> attributeClass;
    private TreeAttribute<T> parent;

    public String getFullName() {
        return parent.getFullName() + name;
    }
}
