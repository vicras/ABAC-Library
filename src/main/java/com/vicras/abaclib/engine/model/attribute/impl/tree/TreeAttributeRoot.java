package com.vicras.abaclib.engine.model.attribute.impl.tree;

import static org.apache.logging.log4j.util.Strings.EMPTY;

import com.vicras.abaclib.engine.model.attribute.TreeAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class TreeAttributeRoot<T> implements TreeAttribute<T> {
    private Class<T> attributeClass;
    private final String name = EMPTY;
    private final String fullName = EMPTY;
    private final TreeAttribute<T> parent = null;
}
