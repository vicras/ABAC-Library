package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class PolicySetResult extends PolicyBaseResult<PolicySet> {

    private final List<PolicyBaseResult<? extends PolicyModel>> policyBaseResult;

    public PolicySetResult(
            PolicySet model,
            CalculationResult result,
            List<PolicyBaseResult<? extends PolicyModel>> policyBaseResult) {
        super(model, result);
        this.policyBaseResult = policyBaseResult;
    }

    @Override
    public Collection<PolicyBaseResult<? extends PolicyModel>> getChild() {
        return policyBaseResult;
    }
}
