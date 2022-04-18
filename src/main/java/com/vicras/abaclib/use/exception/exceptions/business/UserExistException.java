package com.vicras.abaclib.use.exception.exceptions.business;

public class UserExistException extends BusinessException {
    public UserExistException(String user_already_exist) {
        super(user_already_exist);
    }
}
