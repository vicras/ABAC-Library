package com.vicras.abaclib.engine.pdp.model;

import com.vicras.abaclib.engine.model.effect.holder.AdviceWithResult;
import com.vicras.abaclib.engine.model.effect.holder.ObligationWithResult;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;

import java.util.Collection;

public interface DecisionPointResult {
    CalculationResult getFinalCalculationResult();

    Collection<AdviceWithResult> getAdvicesToPerform();

    Collection<ObligationWithResult> getObligationsToPerform();

    Collection<PolicyBaseResult<PolicyModel>> getResults();
}
