package com.vicras.abaclib.engine.pdp.model.impl;

import com.vicras.abaclib.engine.model.effect.holder.AdviceWithResult;
import com.vicras.abaclib.engine.model.effect.holder.ObligationWithResult;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor(staticName = "of")
public class BaseDecisionPointResult implements DecisionPointResult {
    private CalculationResult finalCalculationResult;
    private Collection<AdviceWithResult> advicesToPerform;
    private Collection<ObligationWithResult> obligationsToPerform;
    private Collection<PolicyBaseResult<PolicyModel>> results;
}
