package com.vicras.abaclib.engine.pdp.model;

import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;

import java.util.Collection;

public interface DecisionPointResult {
    CalculationResult getFinalCalculationResult();

    Collection<Advice> getAdvicesToPerform();

    Collection<Obligation> getObligationsToPerform();

    Collection<PolicyBaseResult<PolicyModel>> getResults();
}
