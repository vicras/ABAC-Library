package com.vicras.abaclib.engine.pdp.model.impl;

import com.vicras.abaclib.engine.model.effect.holder.EffectWithResultModel;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.PolicyObjectResult;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data

@AllArgsConstructor(staticName = "of")
public class BaseDecisionPointResult implements DecisionPointResult {
    private CalculationResult finalCalculationResult;
    private Collection<EffectWithResultModel<Advice>> advicesToPerform;
    private Collection<EffectWithResultModel<Obligation>> obligationsToPerform;
    private Collection<PolicyObjectResult<PolicyModel>> results;
}
