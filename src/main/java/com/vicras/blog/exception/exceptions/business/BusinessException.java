package com.vicras.blog.exception.exceptions.business;

import com.vicras.blog.exception.exceptions.AbacUseException;

public abstract class BusinessException extends AbacUseException {
    public BusinessException(String msg) {
        super(msg);
    }
}