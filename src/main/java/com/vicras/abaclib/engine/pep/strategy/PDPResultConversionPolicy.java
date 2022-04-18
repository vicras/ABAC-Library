package com.vicras.abaclib.engine.pep.strategy;

import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.pep.model.PEPResult;

public interface PDPResultConversionPolicy {
    PEPResult interpreterResult(CalculationResult pdpResult);
}
