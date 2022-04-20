package com.vicras.abaclib.engine.model.effect;

import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;

public interface Effect {
    EffectAction getAction();

    EffectChecker<CalculationResult> getEffectChecker();

    BaseModel getModel();
}
