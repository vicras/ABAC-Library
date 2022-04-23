package com.vicras.abaclib.engine.model.main;

import com.vicras.abaclib.engine.model.combinator.Combinator;

import java.util.Collection;

public interface PolicyModel extends ObjectModel {
    Combinator<? extends ObjectModel> getCombinationRule();

    Collection<? extends ObjectModel> getChild();
}
