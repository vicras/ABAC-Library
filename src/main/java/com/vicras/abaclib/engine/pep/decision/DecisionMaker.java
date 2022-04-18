package com.vicras.abaclib.engine.pep.decision;

import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pep.model.PEPResult;

public interface DecisionMaker {
    PEPResult makeDecision(DecisionPointResult pdpResult);
}
