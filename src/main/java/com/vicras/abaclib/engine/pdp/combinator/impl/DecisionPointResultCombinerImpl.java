package com.vicras.abaclib.engine.pdp.combinator.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_DEFINED;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.model.effect.holder.AdviceWithResult;
import com.vicras.abaclib.engine.model.effect.holder.EffectWithResultModel;
import com.vicras.abaclib.engine.model.effect.holder.ObligationWithResult;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.BaseResult;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
import com.vicras.abaclib.engine.pdp.combinator.DecisionPointResultCombinator;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pdp.model.impl.BaseDecisionPointResult;
import com.vicras.abaclib.use.exception.exceptions.internal.InternalException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import org.springframework.stereotype.Component;

/**
 * Check that all Policy model has the same result.
 * Return this result
 * In other case return NOT_DEFINED result.
 */
@Component
public class DecisionPointResultCombinerImpl implements DecisionPointResultCombinator {

    @Override
    public DecisionPointResult getResult(List<PolicyBaseResult<PolicyModel>> results) {
        CalculationResult finalCalcResult = NOT_DEFINED;
        if (!results.isEmpty()) {
            CalculationResult calculationResult = results.get(0).getResult();
            finalCalcResult = results
                    .stream()
                    .allMatch(res -> calculationResult.equals(res.getResult()))
                    ? calculationResult
                    : NOT_DEFINED;
        }

        var obligations = castList(bfsCombinationResult(
                results,
                finalCalcResult,
                BaseModel::getObligations), ObligationWithResult.class);
        var advices = castList(bfsCombinationResult(
                results,
                finalCalcResult,
                BaseModel::getAdvices), AdviceWithResult.class);
        return BaseDecisionPointResult.of(finalCalcResult, advices, obligations, results);
    }

    private <T extends Effect> List<EffectWithResultModel> bfsCombinationResult(
            List<PolicyBaseResult<PolicyModel>> results,
            CalculationResult expectedResult,
            Function<BaseModel, Collection<T>> mapper
    ) {
        var ans = new ArrayList<EffectWithResultModel>();
        Queue<BaseResult<?>> nextPolicyQueue = new LinkedList<>(results);
        while (!nextPolicyQueue.isEmpty()) {
            var resultModel = nextPolicyQueue.poll();
            var child = resultModel.getChild();
            nextPolicyQueue.addAll(child);
            if (expectedResult.equals(resultModel.getResult())) {
                var list = mapper.apply(resultModel.getModel()).stream()
                        .filter(obligation -> obligation.getEffectChecker().check(expectedResult))
                        .map(effect -> createHolder(effect, resultModel))
                        .collect(toList());
                ans.addAll(list);
            }
        }
        Collections.reverse(ans);
        return ans;
    }

    private <T extends Effect> EffectWithResultModel createHolder(T effect, ResultModel model) {
        if (effect instanceof Obligation) {
            return ObligationWithResult.of((Obligation) effect, model);
        }
        if (effect instanceof Advice) {
            return AdviceWithResult.of((Advice) effect, model);
        }
        throw new InternalException("Don't have opportunities to recognize type");
    }

    private <T> List<T> castList(List<?> list, Class<T> clazz) {
        return list.stream().map(clazz::cast).collect(toList());
    }
}
