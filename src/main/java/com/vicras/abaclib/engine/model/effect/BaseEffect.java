package com.vicras.abaclib.engine.model.effect;

import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.Data;

@Data
public abstract class BaseEffect implements Effect {
    protected EffectAction action;
    protected EffectChecker<CalculationResult> effectChecker;
    protected BaseModel model;
}
