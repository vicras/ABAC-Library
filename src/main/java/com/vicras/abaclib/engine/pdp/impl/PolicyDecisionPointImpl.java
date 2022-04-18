package com.vicras.abaclib.engine.pdp.impl;

import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.engine.pdp.PolicyDecisionPoint;
import com.vicras.abaclib.engine.pdp.checker.PolicyChecker;
import com.vicras.abaclib.engine.pdp.checker.util.ConditionCheckerUtil;
import com.vicras.abaclib.engine.pdp.combinator.DecisionPointResultCombinator;
import com.vicras.abaclib.engine.pdp.extractor.PolicyProvider;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PolicyDecisionPointImpl implements PolicyDecisionPoint {
    private final PolicyInformationPoint pip;
    private final PolicyProvider policyProvider;
    private final PolicyChecker policyChecker;
    private final DecisionPointResultCombinator resultCombinator;
    private final ConditionCheckerUtil conditionCheckerUtil;

    @Override
    public DecisionPointResult perform(ExecutionContext context) {
        pip.setExecutionContext(context);
        var policiesToCheck = policyProvider.getAllPolicy().stream()
                .filter(conditionCheckerUtil::checkTargetSafe)
                .map(policyChecker::check)
                .collect(toList());
        return resultCombinator.getResult(policiesToCheck);
    }
}
