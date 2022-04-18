package com.vicras.abaclib.engine.pip.impl;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.provider.AttributeProvider;
import com.vicras.abaclib.engine.model.attribute.provider.AttributeWithContextProvider;
import com.vicras.abaclib.engine.model.attribute.provider.AttributesProvider;
import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.DuringAttributeExtractionException;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import com.vicras.abaclib.engine.pip.reducer.AttributeValuesReducer;
import com.vicras.abaclib.engine.pip.reducer.impl.RandomAttributeValuesReducer;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PolicyInformationPointImpl implements PolicyInformationPoint {

    private final List<AttributeProvider> attributeProviders;
    private final List<AttributesProvider> attributesProviders;
    private final List<AttributeWithContextProvider> attributesWithContextProviders;
    private final ThreadLocal<ExecutionContext> context = new ThreadLocal<>();

    @Autowired(required = false)
    private AttributeValuesReducer reducer = new RandomAttributeValuesReducer();

    @Override
    public <T> T get(Attribute<T> attribute) {
        return reducer.reduceResult(performSearch(attribute), attribute);
    }

    @Override
    public <T> Collection<T> gets(Attribute<T> attribute) {
        return performSearch(attribute);
    }

    private <T> Collection<T> performSearch(Attribute<T> attribute) {
        var attributeValues = Try.of(() -> findAttribute(attribute))
                .getOrElseThrow(DuringAttributeExtractionException::new);
        return resultChecker(attributeValues, attribute);
    }

    private <T> Collection<T> findAttribute(Attribute<T> attribute) {
        var values1 = attributeProviders.stream()
                .map(pip -> pip.getAttribute(attribute));
        var values2 = attributesProviders.stream()
                .flatMap(pip -> pip.getAttributes(attribute).stream());
        var values3 = attributesWithContextProviders.stream()
                .flatMap(pip -> pip.getAttributes(attribute, context.get()).stream());
        return Stream.of(values1, values2, values3).flatMap(identity())
                .collect(toList());
    }

    private <T> Collection<T> resultChecker(Collection<T> attributeValues, Attribute<T> attribute) {
        if (attributeValues.isEmpty()) {
            throw new AttributeNotFoundException(attribute);
        }
        return attributeValues;
    }

    @Override
    public void setExecutionContext(ExecutionContext context) {
        this.context.set(context);
    }
}