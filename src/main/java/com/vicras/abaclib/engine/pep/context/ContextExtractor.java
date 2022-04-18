package com.vicras.abaclib.engine.pep.context;

import com.vicras.abaclib.engine.model.context.ExecutionContext;

import java.util.List;

public interface ContextExtractor {
    ExecutionContext getContext(List<ContextInputValue<?>> inputValues);
}
