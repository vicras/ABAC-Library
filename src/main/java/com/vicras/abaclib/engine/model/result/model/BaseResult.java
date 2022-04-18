package com.vicras.abaclib.engine.model.result.model;

import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public abstract class BaseResult<T extends BaseModel> implements ResultModel {
    private T model;
    private CalculationResult result;

    public Collection<? extends BaseResult<? extends BaseModel>> getChild() {
        return emptyList();
    }
}
