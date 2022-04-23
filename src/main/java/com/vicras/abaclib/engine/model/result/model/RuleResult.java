package com.vicras.abaclib.engine.model.result.model;

import com.vicras.abaclib.engine.model.main.model.Rule;
import com.vicras.abaclib.engine.model.result.CalculationResult;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class RuleResult extends ObjectResult<Rule> {
    public RuleResult(Rule model, CalculationResult result) {
        super(model, result);
    }
}
