package com.vicras.abaclib.use.abac.domain.advice;

import static com.vicras.abaclib.engine.model.result.CalculationResult.ALLOW;

import com.vicras.abaclib.engine.model.effect.impl.Advice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UseAdviceProvider {

    public Advice adviceForExecution() {
        Advice advice = new Advice();
        advice.setAction((rn) -> log.info("Advice was executed for effect ALLOW"));
        advice.setEffectChecker(ALLOW::equals);
        return advice;
    }

    public Advice adviceNotForExecution() {
        Advice advice = new Advice();
        advice.setAction((rm) -> log.error("Advice was executed not for ALLOW"));
        advice.setEffectChecker(res -> !ALLOW.equals(res));
        return advice;
    }

    public Advice adviceForExecutionPolicy() {
        Advice advice = new Advice();
        advice.setAction((rm) -> log.info("Advice was executed for effect ALLOW Policy"));
        advice.setEffectChecker(ALLOW::equals);
        return advice;
    }

    public Advice adviceNotForExecutionPolicy() {
        Advice advice = new Advice();
        advice.setAction((rm) -> log.error("Advice was executed not for ALLOW Policy"));
        advice.setEffectChecker(res -> !ALLOW.equals(res));
        return advice;
    }

    public Advice adviceForExecutionSetPolicy() {
        Advice advice = new Advice();
        advice.setAction((rm) -> log.info("Advice was executed for effect ALLOW Set Policy"));
        advice.setEffectChecker(ALLOW::equals);
        return advice;
    }

    public Advice adviceNotForExecutionSetPolicy() {
        Advice advice = new Advice();
        advice.setAction((rm) -> log.error("Advice was executed not for ALLOW Set Policy"));
        advice.setEffectChecker(res -> !ALLOW.equals(res));
        return advice;
    }
}
