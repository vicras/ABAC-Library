package com.vicras.abaclib.engine.pdp.checker;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.model.PolicyObjectResult;

public interface PolicyChecker {

    PolicyObjectResult<PolicyModel> check(PolicyModel policyModel);
}
