package com.vicras.abaclib.engine.model.effect;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.Data;

@Data
public abstract class BaseEffect implements Effect {
    private String title = EMPTY;
    private String description = EMPTY;

    protected EffectAction action;
    protected EffectChecker<CalculationResult> effectChecker;
}
