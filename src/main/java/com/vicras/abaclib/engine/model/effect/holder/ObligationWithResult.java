package com.vicras.abaclib.engine.model.effect.holder;

import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObligationWithResult extends
        EffectWithResultModel {
    protected Obligation effect;

    public static ObligationWithResult of(Obligation effect, ResultModel resultModel) {
        ObligationWithResult obligationWithResult = new ObligationWithResult();
        obligationWithResult.setEffect(effect);
        obligationWithResult.resultModel = resultModel;
        return obligationWithResult;
    }
}
