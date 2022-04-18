package com.vicras.abaclib.engine.pdp.checker;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;

public interface PolicyChecker {

    PolicyBaseResult<PolicyModel> check(PolicyModel policyModel);
}
