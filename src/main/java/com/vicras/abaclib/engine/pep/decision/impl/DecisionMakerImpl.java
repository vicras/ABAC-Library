package com.vicras.abaclib.engine.pep.decision.impl;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pep.decision.DecisionMaker;
import com.vicras.abaclib.engine.pep.model.PEPResult;
import com.vicras.abaclib.engine.pep.strategy.PDPResultConversionPolicy;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DecisionMakerImpl implements DecisionMaker {

    private final PDPResultConversionPolicy resultConversionPolicy;

    @Override
    public PEPResult makeDecision(DecisionPointResult pdpResult) {
        Consumer<Try<Void>> obligationException = voidTry -> log.warn(
                "Error while execute Obligation",
                voidTry.getCause());
        Consumer<Try<Void>> adviceException = voidTry -> log.warn(
                "Error while execute Advice",
                voidTry.getCause());

        var obligations = pdpResult.getObligationsToPerform();
        var advices = pdpResult.getAdvicesToPerform();

        withErrorsDuringExecution(advices, adviceException);

        var withFailures = withErrorsDuringExecution(obligations, obligationException);
        var pepResult = resultConversionPolicy.interpreterResult(pdpResult.getFinalCalculationResult());
        return considerMistake(pepResult, withFailures);
    }

    private boolean withErrorsDuringExecution(
            Collection<? extends Effect> effects,
            Consumer<Try<Void>> errorConsumer) {
        return !Stream.ofAll(effects)
                .map(obligation -> Try.run(() -> obligation.getAction().run()))
                .filter(Try::isFailure)
                .peek(errorConsumer)
                .isEmpty();
    }

    private PEPResult considerMistake(PEPResult pepResult, boolean withFail) {
        return withFail
                ? pepResult.opposite()
                : pepResult;
    }
}
