package com.vicras.abaclib.use.exception.exceptions.business;

import com.vicras.abaclib.use.exception.exceptions.AbacUseException;

public abstract class BusinessException extends AbacUseException {
    public BusinessException(String msg) {
        super(msg);
    }
}