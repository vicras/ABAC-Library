package com.vicras.abaclib.engine.model.effect;

public interface EffectChecker<T extends Enum<?>> {
    boolean check(T valueToCheck);
}
