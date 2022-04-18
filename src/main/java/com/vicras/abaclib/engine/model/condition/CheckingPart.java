package com.vicras.abaclib.engine.model.condition;

import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.CalculationException;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;

public interface CheckingPart {
    boolean check(PolicyInformationPoint pip)
            throws AttributeNotFoundException, CalculationException;
}
