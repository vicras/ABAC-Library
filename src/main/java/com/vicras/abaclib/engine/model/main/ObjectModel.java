package com.vicras.abaclib.engine.model.main;

import com.vicras.abaclib.engine.model.condition.Target;
import com.vicras.abaclib.engine.model.effect.impl.Advice;
import com.vicras.abaclib.engine.model.effect.impl.Obligation;
import com.vicras.abaclib.engine.model.meta.Informational;

import java.util.Collection;

public interface ObjectModel extends Informational {
    Target getTarget();

    Collection<Obligation> getObligations();

    Collection<Advice> getAdvices();
}
