package com.vicras.abaclib.engine.pdp.combinator.impl;

import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_DEFINED;
import static java.util.stream.Collectors.toList;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.BaseResult;
import com.vicras.abaclib.engine.model.result.model.PolicyBaseResult;
import com.vicras.abaclib.engine.pdp.combinator.DecisionPointResultCombinator;
import com.vicras.abaclib.engine.pdp.model.DecisionPointResult;
import com.vicras.abaclib.engine.pdp.model.impl.BaseDecisionPointResult;

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

        var obligations = bfsCombinationResult(results, finalCalcResult, BaseModel::getObligations);
        var advices = bfsCombinationResult(results, finalCalcResult, BaseModel::getAdvices);
        return BaseDecisionPointResult.of(finalCalcResult, advices, obligations, results);
    }

    private <T extends Effect> List<T> bfsCombinationResult(
            List<PolicyBaseResult<PolicyModel>> results,
            CalculationResult expectedResult,
            Function<BaseModel, Collection<T>> mapper
    ) {
        var ans = new ArrayList<T>();
        Queue<BaseResult<?>> nextPolicyQueue = new LinkedList<>(results);
        while (!nextPolicyQueue.isEmpty()) {
            var resultModel = nextPolicyQueue.poll();
            var child = resultModel.getChild();
            nextPolicyQueue.addAll(child);
            if (expectedResult.equals(resultModel.getResult())) {
                var list = mapper.apply(resultModel.getModel()).stream()
                        .filter(obligation -> obligation.getEffectChecker().check(expectedResult))
                        .collect(toList());
                ans.addAll(list);
            }
        }
        Collections.reverse(ans);
        return ans;
    }
}
