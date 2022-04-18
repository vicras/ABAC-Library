package com.vicras.abaclib.use.exception.exceptions.business;

import com.vicras.abaclib.use.model.BaseEntity;


public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(Class<? extends BaseEntity> aClass, String id) {
        super(String.format("Entity %s with %s not found", aClass.getSimpleName(), id));
    }
}