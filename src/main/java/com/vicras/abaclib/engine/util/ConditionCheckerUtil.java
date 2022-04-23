package com.vicras.abaclib.engine.util;

import static java.lang.Boolean.FALSE;
import static java.lang.String.format;

import com.vicras.abaclib.engine.model.main.ObjectModel;
import com.vicras.abaclib.engine.pip.PolicyInformationPoint;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConditionCheckerUtil {

    private final PolicyInformationPoint pip;

    public boolean checkTargetSafe(ObjectModel policyModel) {

        return Try.of(() -> suitablePolicy(policyModel))
                .onFailure(getThrowableConsumer(policyModel))
                .getOrElse(FALSE);
    }

    private Consumer<Throwable> getThrowableConsumer(ObjectModel policyModel) {
        return ex -> log.warn(format(
                "Exception during target checking for model %s",
                policyModel), ex);
    }

    private boolean suitablePolicy(ObjectModel policyModel) {
        var target = policyModel.getTarget();
        return target.check(pip);
    }
}
