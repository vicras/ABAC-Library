package com.vicras.abaclib.engine.model.combinator.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_APPLICABLE;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.ObjectResult;

import java.util.List;

public class AllowIfOneAllows implements Combinator<PolicyModel> {
    @Override
    public CalculationResult combine(List<? extends ObjectResult<? extends PolicyModel>> baseResults) {
        return baseResults.stream().map(ObjectResult::getResult).anyMatch(ALLOW::equals)
                ? ALLOW
                : NOT_APPLICABLE;
    }
}
