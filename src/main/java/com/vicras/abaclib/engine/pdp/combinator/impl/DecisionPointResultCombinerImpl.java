package com.vicras.abaclib.engine.pdp.combinator.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_DEFINED;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.model.effect.holder.EffectWithResultModel;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.ObjectResult;
import com.vicras.abaclib.engine.model.result.model.PolicyObjectResult;
import com.vicras.abaclib.engine.pdp.combinator.DecisionPointResultCombinator;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pdp.model.impl.BaseDecisionPointResult;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Component;

/**
 * Check that all Policy model has the same result.
 * Return this result
 * In other case return NOT_DEFINED result.
 */
@Component
public class DecisionPointResultCombinerImpl implements DecisionPointResultCombinator {

    private static final int FIRST_RESULT = 0;

    @Override
    public DecisionPointResult getResult(List<PolicyObjectResult<PolicyModel>> results) {
        CalculationResult finalCalcResult = getFinalCalcResult(results);
        var effects = bfsCombinationResult(results, finalCalcResult);
        return BaseDecisionPointResult.of(finalCalcResult, effects._1, effects._2, results);
    }

    private CalculationResult getFinalCalcResult(List<PolicyObjectResult<PolicyModel>> results) {
        if (results.isEmpty()) {
            return NOT_DEFINED;
        }

        // Get random (first) result and compare with others
        CalculationResult calculationResult = results.get(FIRST_RESULT).getResult();
        return results.stream()
                .allMatch(res -> calculationResult.equals(res.getResult()))
                ? calculationResult
                : NOT_DEFINED;
    }

    private Tuple2<List<EffectWithResultModel<Advice>>, List<EffectWithResultModel<Obligation>>>
    bfsCombinationResult(
            List<PolicyObjectResult<PolicyModel>> results,
            CalculationResult expectedResult
    ) {
        List<EffectWithResultModel<Advice>> advices = new ArrayList<>();
        List<EffectWithResultModel<Obligation>> obligations = new ArrayList<>();

        var ans = new ArrayList<Tuple>();

        Queue<ObjectResult<?>> nextPolicyQueue = new LinkedList<>(results);
        while (!nextPolicyQueue.isEmpty()) {
            var resultModel = nextPolicyQueue.poll();
            var model = resultModel.getModel();
            var result = resultModel.getResult();

            nextPolicyQueue.addAll(resultModel.getChild());

            if (expectedResult.equals(result)) {
                var tempObl = prepareEffects(expectedResult, resultModel, model.getObligations());
                var tempAdvice = prepareEffects(expectedResult, resultModel, model.getAdvices());

                advices.addAll(tempAdvice);
                obligations.addAll(tempObl);
            }
        }
        Collections.reverse(ans);
        Collections.reverse(ans);
        return Tuple.of(advices, obligations);
    }

    private <T extends Effect> List<EffectWithResultModel<T>> prepareEffects(
            CalculationResult expectedResult,
            ObjectResult<?> resultModel,
            Collection<T> effects) {
        return effects.stream()
                .filter(effect -> effect.getEffectChecker().check(expectedResult))
                .map(effect -> EffectWithResultModel.of(resultModel, effect))
                .collect(toList());
    }
}
