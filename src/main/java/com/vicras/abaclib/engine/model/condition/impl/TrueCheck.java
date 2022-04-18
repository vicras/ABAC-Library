package com.vicras.abaclib.engine.model.condition.impl;

import static lombok.AccessLevel.PRIVATE;

import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.CalculationException;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = PRIVATE)
public class TrueCheck implements Target, Condition {
    public static TrueCheck get() {
        return new TrueCheck();
    }

    @Override
    public boolean check(PolicyInformationPoint pip)
            throws AttributeNotFoundException, CalculationException {
        return true;
    }
}
