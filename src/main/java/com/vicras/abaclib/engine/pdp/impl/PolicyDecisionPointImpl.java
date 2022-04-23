package com.vicras.abaclib.engine.pdp.impl;

import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.context.ExecutionContext;
import com.vicras.abaclib.engine.pdp.PolicyDecisionPoint;
import com.vicras.abaclib.engine.pdp.checker.PolicyChecker;
import com.vicras.abaclib.engine.util.ConditionCheckerUtil;
import com.vicras.abaclib.engine.pdp.combinator.DecisionPointResultCombinator;
import com.vicras.abaclib.engine.pdp.extractor.PolicyProvider;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyDecisionPointImpl implements PolicyDecisionPoint {
    private final PolicyInformationPoint pip;
    private final PolicyProvider policyProvider;
    private final PolicyChecker policyChecker;
    private final DecisionPointResultCombinator resultCombinator;
    private final ConditionCheckerUtil conditionCheckerUtil;

    @PostConstruct
    private void logUsedReducerType(){
        log.info("In {} used {} Policy provider",getClass(), policyProvider.getClass());
        log.info("In {} used {} Result combiner",getClass(), resultCombinator.getClass());
    }

    @Override
    public DecisionPointResult perform(ExecutionContext context) {
        log.debug("Decision point started with execution context: {}", context);
        pip.setExecutionContext(context);
        var policiesToCheck = policyProvider.getAllPolicy().stream()
                .filter(conditionCheckerUtil::checkTargetSafe)
                .map(policyChecker::check)
                .collect(toList());
        DecisionPointResult result = resultCombinator.getResult(policiesToCheck);

        log.debug("Decision point ended with result: {}", result);
        return result;
    }
}
