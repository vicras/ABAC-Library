package com.vicras.abaclib.engine.model.result.model;

import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.main.ObjectModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public abstract class ObjectResult<T extends ObjectModel> implements ResultModel {
    private T model;
    private CalculationResult result;

    public Collection<? extends ObjectResult<? extends ObjectModel>> getChild() {
        return emptyList();
    }
}
