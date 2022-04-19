package com.vicras.abaclib.engine.model.main.model;

import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.combinator.Combinator;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.BaseModel;
import com.vicras.abaclib.engine.model.main.PolicyModel;
import lombok.Data;

import java.util.Collection;

@Data
public class PolicySet implements PolicyModel {

    private Target target;

    private Collection<PolicyModel> policies;

    private Combinator<PolicyModel> combinationRule;

    private Collection<Obligation> obligations = emptyList();
    private Collection<Advice> advices = emptyList();

    @Override
    public Collection<? extends BaseModel> getChild() {
        return policies;
    }
}
