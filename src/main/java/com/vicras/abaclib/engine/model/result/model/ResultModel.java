package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.ObjectModel;
import com.vicras.abaclib.engine.model.result.CalculationResult;

public interface ResultModel {
    ObjectModel getModel();

    CalculationResult getResult();
}
