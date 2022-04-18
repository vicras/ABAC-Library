package com.vicras.abaclib.engine.pdp.checker.util;

import static java.lang.Boolean.FALSE;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConditionCheckerUtil {
    private final PolicyInformationPoint pip;

    public boolean checkTargetSafe(PolicyModel policyModel) {
        return Try.of(() -> suitablePolicy(policyModel))
                .getOrElse(FALSE);
    }

    private boolean suitablePolicy(PolicyModel policyModel) {
        var target = policyModel.getTarget();
        return target.check(pip);
    }
}
