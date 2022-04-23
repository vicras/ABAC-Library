package com.vicras.abaclib.engine.model.result.model;

import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.main.ObjectModel;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public abstract class PolicyObjectResult<T extends PolicyModel> extends ObjectResult<T> {
    public PolicyObjectResult(T model, CalculationResult result) {
        super(model, result);
    }

    public Collection<? extends ObjectResult<? extends ObjectModel>> getChild() {
        return emptyList();
    }
}
