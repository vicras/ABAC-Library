package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.CalculationResult;

public class RuleResult extends BaseResult<Rule> {
    public RuleResult(Rule model, CalculationResult result) {
        super(model, result);
    }
}
