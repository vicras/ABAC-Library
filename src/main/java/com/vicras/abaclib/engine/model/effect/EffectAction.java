package com.vicras.abaclib.engine.model.effect;

import com.vicras.abaclib.engine.model.result.model.ResultModel;

@FunctionalInterface
public interface EffectAction {
    void run(ResultModel rm) throws Exception;
}
