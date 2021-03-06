package com.vicras.abaclib.engine.model.effect;

import com.vicras.abaclib.engine.model.meta.Informational;
import com.vicras.abaclib.engine.model.result.CalculationResult;

public interface Effect extends Informational {
    EffectAction getAction();

    EffectChecker<CalculationResult> getEffectChecker();
}
