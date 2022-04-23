package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PolicyResult extends PolicyObjectResult<Policy> {
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
