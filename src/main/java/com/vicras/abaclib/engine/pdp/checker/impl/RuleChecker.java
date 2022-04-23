package com.vicras.abaclib.engine.pdp.checker.impl;

import static com.vicras.abaclib.engine.model.RuleEffect.PERMIT;
import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;
import static com.vicras.abaclib.engine.model.result.CalculationResult.DENY;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_APPLICABLE;
import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_DEFINED;
import static java.lang.String.format;

import com.vicras.abaclib.engine.model.RuleEffect;
import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.exception.AttributeNotFoundException;
import com.vicras.abaclib.engine.model.exception.CalculationException;
import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.model.RuleResult;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RuleChecker {

    private final PolicyInformationPoint pip;

    public RuleResult check(Rule rule) {
        return Try.of(() -> getRuleResult(rule))
                .onFailure(AttributeNotFoundException.class, onFailLog(rule))
                .onFailure(CalculationException.class, onFailLog(rule))
                .onSuccess(this::onSuccessLog)
                .getOrElse(new RuleResult(rule, NOT_DEFINED));
    }

    private Consumer<Throwable> onFailLog(Rule rule) {
        return ex -> log.warn(
                format("Exception during performing rule %s with exception", rule),
                ex);
    }

    private void onSuccessLog(RuleResult result) {
        log.debug("Result of execution rule {} is {}", result.getModel(), result.getResult());
    }

    private RuleResult getRuleResult(Rule rule) {
        Target target = rule.getTarget();
        Condition condition = rule.getCondition();
        RuleEffect effect = rule.getEffect();

        boolean positiveEvaluate = target.check(pip) && condition.check(pip);
        if (positiveEvaluate) {
            var calc = PERMIT.equals(effect) ? ALLOW : DENY;
            return new RuleResult(rule, calc);
        } else {
            return new RuleResult(rule, NOT_APPLICABLE);
        }
    }
}
