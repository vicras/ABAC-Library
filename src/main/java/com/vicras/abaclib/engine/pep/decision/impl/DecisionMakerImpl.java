package com.vicras.abaclib.engine.pep.decision.impl;

import static java.lang.String.format;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.model.effect.EffectAction;
import com.vicras.abaclib.engine.model.effect.holder.EffectWithResultModel;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
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
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DecisionMakerImpl implements DecisionMaker {

    private final PDPResultConversionPolicy resultConversionPolicy;

    @PostConstruct
    private void logResultConversionPolicy() {
        log.info("Strategy PEP result conversion {}", resultConversionPolicy.getClass());
    }

    @Override
    public PEPResult makeDecision(DecisionPointResult pdpResult) {
        var obligations = pdpResult.getObligationsToPerform();
        var advices = pdpResult.getAdvicesToPerform();

        withErrorsDuringExecution(advices, failDuringExecution("Advices"));

        var withFailures = withErrorsDuringExecution(
                obligations,
                failDuringExecution("Obligation"));
        var pepResult = resultConversionPolicy.interpreterResult(pdpResult.getFinalCalculationResult());
        return considerMistake(pepResult, withFailures);
    }

    private Consumer<Try<Void>> failDuringExecution(String object) {
        return voidTry -> log.warn(format("Error while execute %s", object), voidTry.getCause());
    }

    private boolean withErrorsDuringExecution(
            Collection<? extends EffectWithResultModel<? extends Effect>> effects,
            Consumer<Try<Void>> logFailure) {
        return !Stream.ofAll(effects)
                .map(this::executeEffectSafe)
                .filter(Try::isFailure)
                .peek(logFailure)
                .isEmpty();
    }

    private Try<Void> executeEffectSafe(EffectWithResultModel<?> effectAndModel) {
        ResultModel model = effectAndModel.getResultModel();

        Effect effect = effectAndModel.getEffect();
        EffectAction action = effect.getAction();
        return Try.run(() -> action.run(model));
    }

    private PEPResult considerMistake(PEPResult pepResult, boolean withFail) {
        return withFail
                ? pepResult.opposite()
                : pepResult;
    }
}
