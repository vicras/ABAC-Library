package com.vicras.abaclib.engine.model.combinator.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_APPLICABLE;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.ObjectResult;

import java.util.List;

public class AllowIfTwoAllows implements Combinator<Rule> {
    @Override
    public CalculationResult combine(List<? extends ObjectResult<? extends Rule>> baseResults) {
        return baseResults.stream().map(ObjectResult::getResult).filter(ALLOW::equals)
                .count() == 2
                ? ALLOW
                : NOT_APPLICABLE;
    }
}
