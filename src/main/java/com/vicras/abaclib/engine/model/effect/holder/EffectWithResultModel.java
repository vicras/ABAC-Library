package com.vicras.abaclib.engine.model.effect.holder;

import com.vicras.abaclib.engine.model.effect.Effect;
import com.vicras.abaclib.engine.model.result.model.ResultModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EffectWithResultModel<T extends Effect> {
    private ResultModel resultModel;
    private T effect;
}
