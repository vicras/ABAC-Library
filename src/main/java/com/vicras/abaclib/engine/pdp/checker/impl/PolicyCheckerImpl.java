package com.vicras.abaclib.engine.pdp.checker.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_APPLICABLE;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.model.PolicyObjectResult;
import com.vicras.abaclib.engine.model.result.model.PolicyResult;
import com.vicras.abaclib.engine.model.result.model.PolicySetResult;
import com.vicras.abaclib.engine.pdp.checker.PolicyChecker;
import com.vicras.abaclib.engine.util.ConditionCheckerUtil;
import io.vavr.API;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyCheckerImpl implements PolicyChecker {

    private final RuleChecker ruleChecker;
    private final ConditionCheckerUtil checkerUtil;

    @Override
    @SuppressWarnings(value = "unchecked")
    public PolicyObjectResult<PolicyModel> check(PolicyModel policyModel) {
        log.debug("Start PolicyModel {} check", policyModel);
        PolicyObjectResult<? extends PolicyModel> result = checkPolicyModel(policyModel);
        log.debug("Start PolicyModel {} check", policyModel);
        return (PolicyObjectResult<PolicyModel>) result;
    }

    private PolicyObjectResult<? extends PolicyModel> checkPolicyModel(PolicyModel pol) {
        return API.Match(pol).of(
                Case($(instanceOf(Policy.class)), this::checkPolicy),
                Case($(instanceOf(PolicySet.class)), this::checkPolicySet)
        );
    }

    public PolicySetResult checkPolicySet(PolicySet policySet) {
        Combinator<PolicyModel> combinator = policySet.getCombinationRule();
        Collection<PolicyModel> policies = policySet.getPolicies();

        List<PolicyObjectResult<? extends PolicyModel>> ans = new ArrayList<>();

        boolean positiveEvaluate = checkerUtil.checkTargetSafe(policySet);
        if (positiveEvaluate) {
            policies.forEach(policyModel -> ans.add(checkPolicyModel(policyModel)));
            return new PolicySetResult(policySet, combinator.combine(ans), ans);
        } else {
            return new PolicySetResult(policySet, NOT_APPLICABLE, emptyList());
        }
    }

    public PolicyResult checkPolicy(Policy policy) {
        Combinator<Rule> combinationRule = policy.getCombinationRule();
        Collection<Rule> rules = policy.getRules();

        boolean positiveEvaluate = checkerUtil.checkTargetSafe(policy);
        if (positiveEvaluate) {
            var rulesResults = rules.stream().map(ruleChecker::check).collect(toList());
            return new PolicyResult(policy, combinationRule.combine(rulesResults), rulesResults);
        } else {
            return new PolicyResult(policy, NOT_APPLICABLE, emptyList());
        }
    }
}
