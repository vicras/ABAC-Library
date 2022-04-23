package com.vicras.abaclib.engine.pep.strategy.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static com.vicras.abaclib.engine.pep.model.PEPResult.REFUSE;
import static com.vicras.abaclib.engine.pep.model.PEPResult.SUBMIT;

import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.pep.model.PEPResult;
import com.vicras.abaclib.engine.pep.strategy.PDPResultConversionPolicy;

/**
 * Submit only if result of execution - ALLOW
 * in other cases - REFUSE.
 */
public class DenyBasedStrategy implements PDPResultConversionPolicy {
    @Override
    public PEPResult interpreterResult(CalculationResult pdpResult) {
        return ALLOW.equals(pdpResult)
                ? SUBMIT
                : REFUSE;
    }
}
