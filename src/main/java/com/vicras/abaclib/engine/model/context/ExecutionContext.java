package com.vicras.abaclib.engine.model.context;

import java.util.List;

public interface ExecutionContext {
    <T> List<T> getValue(Class<T> clazz);
}
