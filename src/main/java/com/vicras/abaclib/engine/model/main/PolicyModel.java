package com.vicras.abaclib.engine.model.main;

import com.vicras.abaclib.engine.model.combinator.Combinator;

import java.util.Collection;

public interface PolicyModel extends BaseModel {
    Combinator<? extends BaseModel> getCombinationRule();

    Collection<? extends BaseModel> getChild();
}
