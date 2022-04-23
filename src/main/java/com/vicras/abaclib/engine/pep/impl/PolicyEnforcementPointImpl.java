package com.vicras.abaclib.engine.pep.impl;

import static com.vicras.abaclib.engine.pep.model.PEPResult.SUBMIT;

import com.vicras.abaclib.engine.pdp.PolicyDecisionPoint;
import com.vicras.abaclib.engine.pep.PolicyEnforcementPoint;
import com.vicras.abaclib.engine.pep.context.ContextExtractor;
import com.vicras.abaclib.engine.pep.context.ContextInputValue;
import com.vicras.abaclib.engine.pep.decision.DecisionMaker;
import com.vicras.abaclib.engine.pep.model.PEPResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyEnforcementPointImpl implements PolicyEnforcementPoint {

    private final PolicyDecisionPoint pdp;
    private final ContextExtractor contextExtractor;
    private final DecisionMaker decisionMaker;

    @Override
    public boolean isAllowed(ContextInputValue<?>... inputValues) {
        log.debug("ABAC for input values {}", Arrays.toString(inputValues));
        var context = contextExtractor.getContext(Arrays.asList(inputValues));
        var pdpResult = pdp.perform(context);
        var decision = decisionMaker.makeDecision(pdpResult);
        log.debug("ABAC result {}", decision);
        return convertDecision(decision);
    }

    private boolean convertDecision(PEPResult decision) {
        return SUBMIT.equals(decision);
    }
}
