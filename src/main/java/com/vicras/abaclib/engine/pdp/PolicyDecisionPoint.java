package com.vicras.abaclib.engine.pdp;

import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;

public interface PolicyDecisionPoint {
    DecisionPointResult perform(ExecutionContext context);
}
