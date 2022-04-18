package com.vicras.abaclib.engine.pep.context.impl;

import com.vicras.abaclib.engine.pep.context.ContextInputValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class DefaultInputValue<T> implements ContextInputValue<T> {
    private final T value;
}
