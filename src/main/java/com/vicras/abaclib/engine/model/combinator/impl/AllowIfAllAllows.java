package com.vicras.abaclib.engine.model.combinator.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_APPLICABLE;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.ObjectResult;

import java.util.List;

public class AllowIfAllAllows implements Combinator<Rule> {
    @Override
    public CalculationResult combine(List<? extends ObjectResult<? extends Rule>> results) {
        return results.stream().allMatch(res -> ALLOW.equals(res.getResult()))
                ? ALLOW
                : NOT_APPLICABLE;
    }
}