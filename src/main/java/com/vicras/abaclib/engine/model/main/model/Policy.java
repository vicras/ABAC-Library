package com.vicras.abaclib.engine.model.main.model;

import static com.vicras.abaclib.engine.model.result.CalculationResult.NOT_DEFINED;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.ObjectModel;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import lombok.Data;

import java.util.Collection;

@Data
public class Policy implements PolicyModel {

    private String title = EMPTY;
    private String description = EMPTY;

    private Target target = (nil) -> false;

    private Collection<Rule> rules = emptyList();

    private Combinator<Rule> combinationRule = (res) -> NOT_DEFINED;

    private Collection<Obligation> obligations = emptyList();
    private Collection<Advice> advices = emptyList();

    @Override
    public Collection<? extends ObjectModel> getChild() {
        return rules;
    }
}
