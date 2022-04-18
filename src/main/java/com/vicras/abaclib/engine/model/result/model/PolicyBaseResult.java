package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;

import java.util.Collection;

public abstract class PolicyBaseResult<T extends PolicyModel> extends BaseResult<T> {
    public PolicyBaseResult(T model, CalculationResult result) {
        super(model, result);
    }

    public abstract Collection<? extends BaseResult<?>> getChild();
}
