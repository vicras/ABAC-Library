package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class PolicyResult extends PolicyBaseResult<Policy> {
    private final List<RuleResult> ruleResults;

    public PolicyResult(Policy model, CalculationResult result, List<RuleResult> ruleResults) {
        super(model, result);
        this.ruleResults = ruleResults;
    }

    @Override
    public Collection<RuleResult> getChild() {
        return ruleResults;
    }
}
