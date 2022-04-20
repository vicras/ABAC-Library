package com.vicras.abaclib.engine.model.effect.holder;

import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdviceWithResult extends
        EffectWithResultModel {
    protected Advice effect;

    public static AdviceWithResult of(Advice effect, ResultModel resultModel) {
        AdviceWithResult obligationWithResult = new AdviceWithResult();
        obligationWithResult.setEffect(effect);
        obligationWithResult.resultModel = resultModel;
        return obligationWithResult;
    }
}
