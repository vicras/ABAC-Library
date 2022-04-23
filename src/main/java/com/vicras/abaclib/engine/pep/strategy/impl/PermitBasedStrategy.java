package com.vicras.abaclib.engine.pep.strategy.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.DENY;
import static com.vicras.abaclib.engine.pep.model.PEPResult.REFUSE;
import static com.vicras.abaclib.engine.pep.model.PEPResult.SUBMIT;

import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.pep.model.PEPResult;
import com.vicras.abaclib.engine.pep.strategy.PDPResultConversionPolicy;

/**
 * REFUSE only if result of execution - DENY
 * in other cases - SUBMIT.
 */
public class PermitBasedStrategy implements PDPResultConversionPolicy {
    @Override
    public PEPResult interpreterResult(CalculationResult pdpResult) {
        return DENY.equals(pdpResult)
                ? REFUSE
                : SUBMIT;
    }
}
