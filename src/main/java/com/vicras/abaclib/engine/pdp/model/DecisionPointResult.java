package com.vicras.abaclib.engine.pdp.model;

import com.vicras.abaclib.engine.model.effect.holder.EffectWithResultModel;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.PolicyObjectResult;

import java.util.Collection;

public interface DecisionPointResult {
    CalculationResult getFinalCalculationResult();

    Collection<EffectWithResultModel<Advice>> getAdvicesToPerform();

    Collection<EffectWithResultModel<Obligation>> getObligationsToPerform();

    Collection<PolicyObjectResult<PolicyModel>> getResults();
}
