package com.vicras.abaclib.engine.model.condition.impl;

import static lombok.AccessLevel.PRIVATE;

import com.vicras.abaclib.engine.model.attribute.Attribute;
import com.vicras.abaclib.engine.model.condition.CheckingPart;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.CalculationException;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@AllArgsConstructor(access = PRIVATE)
public class EnumEqualsCheck<T extends Enum<T>> implements CheckingPart {

    private final Enum<T>[] expectedValues;
    private final Attribute<T> attribute;

    @SafeVarargs
    public static <T extends Enum<T>> EnumEqualsCheck<T> of(
            Attribute<T> attribute,
            Enum<T>... expectedValues) {
        return new EnumEqualsCheck<T>(expectedValues, attribute);
    }

    @Override
    public boolean check(PolicyInformationPoint pip)
            throws AttributeNotFoundException, CalculationException {
        return Arrays.stream(expectedValues)
                .anyMatch(anyEquals(pip));
    }

    private Predicate<? super Enum<?>> anyEquals(PolicyInformationPoint provider) {
        return expectedValues -> expectedValues.equals(provider.get(attribute));
    }
}
