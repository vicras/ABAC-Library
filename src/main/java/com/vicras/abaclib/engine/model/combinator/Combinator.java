package com.vicras.abaclib.engine.model.combinator;

import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.BaseResult;

import java.util.List;

public interface Combinator<T extends BaseModel> {
    CalculationResult combine(List<? extends BaseResult<? extends T>> results);
}
