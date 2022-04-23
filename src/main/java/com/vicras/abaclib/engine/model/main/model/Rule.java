package com.vicras.abaclib.engine.model.main.model;

import static com.vicras.abaclib.engine.model.RuleEffect.PERMIT;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vicras.abaclib.engine.model.RuleEffect;
import com.vicras.abaclib.engine.model.condition.Condition;
import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.main.ObjectModel;
import lombok.Data;

import java.util.Collection;

@Data
public class Rule implements ObjectModel {

    private String title = EMPTY;
    private String description = EMPTY;

    private Target target = (nil) -> false;
    private Condition condition = (nil) -> false;

    private RuleEffect effect = PERMIT;

    private Collection<Obligation> obligations = emptyList();
    private Collection<Advice> advices = emptyList();
}
