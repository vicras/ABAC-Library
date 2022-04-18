package com.vicras.abaclib.engine.model.main;

import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;

import java.util.Collection;

public interface BaseModel {
    Target getTarget();

    Collection<Obligation> getObligations();

    Collection<Advice> getAdvices();
}
