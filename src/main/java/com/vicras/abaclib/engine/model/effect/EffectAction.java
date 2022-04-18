package com.vicras.abaclib.engine.model.effect;

@FunctionalInterface
public interface EffectAction {
    void run() throws Exception;
}
