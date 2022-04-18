package com.vicras.abaclib.engine.model.main.model;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import lombok.Data;

import java.util.Collection;

@Data
public class Policy implements PolicyModel {

    private Target target;

    private Collection<Rule> rules;

    private Combinator<Rule> combinationRule;

    private Collection<Obligation> obligations;
    private Collection<Advice> advices;

    @Override
    public Collection<? extends BaseModel> getChild() {
        return rules;
    }
}
