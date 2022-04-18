package com.vicras.abaclib.engine.pep.model;

public enum PEPResult {
    SUBMIT,
    REFUSE;

    public PEPResult opposite() {
        return equals(SUBMIT)
                ? REFUSE
                : SUBMIT;
    }
}
