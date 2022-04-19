package com.vicras.abaclib.engine.pdp.checker.impl;

import static com.vicras.abaclib.engine.model.RuleEffect.PERMIT;
import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static com.vicras.abaclib.engine.model.result.CalculationResult.DENY;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_APPLICABLE;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_DEFINED;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.run;
import static io.vavr.Predicates.instanceOf;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.RuleEffect;
import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.CalculationException;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.main.model.Policy;
import com.vicras.abaclib.engine.model.main.model.PolicySet;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;
import com.vicras.abaclib.engine.model.result.model.PolicyResult;
import com.vicras.abaclib.engine.model.result.model.PolicySetResult;
import com.vicras.abaclib.engine.model.result.model.RuleResult;
import com.vicras.abaclib.engine.pdp.checker.PolicyChecker;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
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

    private final PolicyInformationPoint pip;

    @Override
    public PolicyBaseResult<PolicyModel> check(PolicyModel policyModel) {
        return (PolicyBaseResult<PolicyModel>) API.Match(policyModel).of(
                Case($(instanceOf(Policy.class)), this::checkPolicy),
                Case($(instanceOf(PolicySet.class)), this::checkPolicySet),
                Case($(), o -> run(() -> {
                    throw new IllegalArgumentException();
                }))
        );
    }

    public RuleResult checkRule(Rule rule) {
        Target target = rule.getTarget();
        Condition condition = rule.getCondition();
        RuleEffect effect = rule.getEffect();
        try {
            boolean positiveEvaluate = target.check(pip) && condition.check(pip);
            if (positiveEvaluate) {
                var calc = PERMIT.equals(effect) ? ALLOW : DENY;
                return new RuleResult(rule, calc);
            } else {
                return new RuleResult(rule, NOT_APPLICABLE);
            }
        } catch (AttributeNotFoundException | CalculationException ex) {
            log.warn("Exception during performing rule", ex);
            return new RuleResult(rule, NOT_DEFINED);
        }
    }

    public PolicyResult checkPolicy(Policy policy) {
        Target target = policy.getTarget();
        Combinator<Rule> combinationRule = policy.getCombinationRule();
        Collection<Rule> rules = policy.getRules();

        boolean positiveEvaluate = target.check(pip);
        if (positiveEvaluate) {
            var rulesResults = rules.stream().map(this::checkRule).collect(toList());
            return new PolicyResult(policy, combinationRule.combine(rulesResults), rulesResults);
        } else {
            return new PolicyResult(policy, NOT_APPLICABLE, emptyList());
        }
    }

    public PolicySetResult checkPolicySet(PolicySet policySet) {
        Target target = policySet.getTarget();
        Combinator<PolicyModel> combinator = policySet.getCombinationRule();
        Collection<PolicyModel> policies = policySet.getPolicies();
        List<PolicyBaseResult<? extends PolicyModel>> ans = new ArrayList<>();
        boolean positiveEvaluate = target.check(pip);
        if (positiveEvaluate) {
            for (var pol : policies) {
                if (pol instanceof Policy) {
                    ans.add(checkPolicy((Policy) pol));
                }
                if (pol instanceof PolicySet) {
                    ans.add(checkPolicySet((PolicySet) pol));
                }
            }
            return new PolicySetResult(policySet, combinator.combine(ans), ans);
        } else {
            return new PolicySetResult(policySet, NOT_APPLICABLE, emptyList());
        }
    }
}
