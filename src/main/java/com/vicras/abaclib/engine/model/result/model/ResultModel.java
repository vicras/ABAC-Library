package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;

public interface ResultModel {
    BaseModel getModel();

    CalculationResult getResult();
}
