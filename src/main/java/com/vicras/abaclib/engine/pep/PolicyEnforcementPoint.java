package com.vicras.abaclib.engine.pep;

import com.vicras.abaclib.engine.pep.context.ContextInputValue;

public interface PolicyEnforcementPoint {
    boolean isAllowed(ContextInputValue<?>... inputValue);
}
