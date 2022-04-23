package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PolicySetResult extends PolicyObjectResult<PolicySet> {

    private final List<PolicyObjectResult<? extends PolicyModel>> policyBaseResult;

    public PolicySetResult(
            PolicySet model,
            CalculationResult result,
            List<PolicyObjectResult<? extends PolicyModel>> policyBaseResult) {
        super(model, result);
        this.policyBaseResult = policyBaseResult;
    }

    @Override
    public Collection<PolicyObjectResult<? extends PolicyModel>> getChild() {
        return policyBaseResult;
    }
}
