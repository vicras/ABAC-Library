package com.vicras.abaclib.engine.model.context.impl;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.context.ExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;

public class ByClassContextImpl implements ExecutionContext {

    Map<Class<?>, List<Object>> context = new HashMap<>();

    public void putValue(Object object) {
        context.merge(object.getClass(), List.of(object), ListUtils::union);
    }

    public <T> List<T> getValue(Class<T> clazz) {
        return ofNullable(context.get(clazz))
                .map(List::stream)
                .stream()
                .flatMap(identity())
                .map(clazz::cast)
                .collect(toList());
    }
}
