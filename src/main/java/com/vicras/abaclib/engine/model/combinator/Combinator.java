package com.vicras.abaclib.engine.model.combinator;

import com.vicras.abaclib.engine.model.main.ObjectModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import com.vicras.abaclib.engine.model.result.model.ObjectResult;

import java.util.List;

public interface Combinator<T extends ObjectModel> {
    CalculationResult combine(List<? extends ObjectResult<? extends T>> results);
}
