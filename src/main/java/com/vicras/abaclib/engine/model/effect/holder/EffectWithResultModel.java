package com.vicras.abaclib.engine.model.effect.holder;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
import lombok.Getter;

@Getter
public abstract class EffectWithResultModel {
    protected ResultModel resultModel;

    public abstract Effect getEffect();
}
