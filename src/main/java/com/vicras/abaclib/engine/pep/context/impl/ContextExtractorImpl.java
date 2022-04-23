package com.vicras.abaclib.engine.pep.context.impl;

import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.engine.model.context.impl.ByClassContextImpl;
import com.vicras.abaclib.engine.pep.context.ContextExtractor;
import com.vicras.abaclib.engine.pep.context.ContextInputValue;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ContextExtractorImpl implements ContextExtractor {
    @Override
    public ExecutionContext getContext(List<ContextInputValue<?>> inputValues) {
        var context = new ByClassContextImpl();
        inputValues.stream()
                .map(ContextInputValue::getValue)
                .forEach(context::putValue);
        return context;
    }
}
