package com.vicras.abaclib.engine.model.main.model;

import static java.util.Collections.emptyList;

import com.vicras.abaclib.engine.model.RuleEffect;
import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.BaseModel;
import lombok.Data;

import java.util.Collection;

@Data
public class Rule implements BaseModel {

    private Target target;
    private Condition condition;

    private RuleEffect effect;

    private Collection<Obligation> obligations = emptyList();
    private Collection<Advice> advices = emptyList();
}
