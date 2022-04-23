package com.vicras.abaclib.engine.pip.impl;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.attribute.provider.AttributeSupplier;
import com.vicras.abaclib.engine.model.attribute.provider.ContextAttributeSupplier;
import com.vicras.abaclib.engine.model.attribute.provider.AttributesSupplier;
import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.AttributeExtractionException;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import com.vicras.abaclib.engine.pip.reducer.AttributeValuesReducer;
import com.vicras.abaclib.engine.pip.reducer.impl.RandomAttributeValuesReducer;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyInformationPointImpl implements PolicyInformationPoint {

    private final List<AttributeSupplier> attributeSuppliers;
    private final List<AttributesSupplier> attributesProviders;
    private final List<ContextAttributeSupplier> attributesWithContextProviders;
    private final ThreadLocal<ExecutionContext> context = new ThreadLocal<>();

    @Autowired(required = false)
    private AttributeValuesReducer reducer = new RandomAttributeValuesReducer();

    @PostConstruct
    private void logUsedReducerType(){
        log.info("In {} used {} implementation of value reducer",getClass(), reducer.getClass());
    }

    @Override
    public <T> T get(Attribute<T> attribute) {
        return reducer.reduceResult(performSearch(attribute), attribute);
    }

    @Override
    public <T> Collection<T> gets(Attribute<T> attribute) {
        return performSearch(attribute);
    }

    private <T> Collection<T> performSearch(Attribute<T> attribute) {
        log.debug("Start searching attribute {}", attribute);
        var attributeValues = findAttribute(attribute);
        var result = resultChecker(attributeValues, attribute);
        log.debug("End searching attribute {} with results {}", attribute, result);
        return result;
    }

    private <T> Collection<T> findAttribute(Attribute<T> attribute) {
        var values1 = attributeSuppliers.stream()
                .flatMap(pip -> findSafe(()->List.of(pip.getAttribute(attribute)), attribute));
        var values2 = attributesProviders.stream()
                .flatMap(pip -> findSafe(()->pip.getAttributes(attribute), attribute));
        var values3 = attributesWithContextProviders.stream()
                .flatMap(pip -> findSafe(()->pip.getAttributes(attribute, context.get()), attribute));
        return Stream.of(values1, values2, values3).flatMap(identity())
                .collect(toList());
    }

    private <T> Stream<T> findSafe(CheckedFunction0<Collection<T>> attributes, Attribute<T> attr){
         return Try.of(attributes)
                .onFailure(ex -> log.warn(format("Error during finding attribute %s", attr), ex))
                .recover(ex -> emptyList())
                .map(Collection::stream)
                .get();
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