package com.vicras.abaclib.engine.pdp.combinator;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.model.PolicyObjectResult;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;

import java.util.List;

public interface DecisionPointResultCombinator {
    DecisionPointResult getResult(List<PolicyObjectResult<PolicyModel>> result);
}
