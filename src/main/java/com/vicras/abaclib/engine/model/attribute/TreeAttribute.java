package com.vicras.abaclib.engine.model.attribute;

public interface TreeAttribute<T> extends StringAttribute<T> {
    TreeAttribute<T> getParent();

    String getFullName();
}
