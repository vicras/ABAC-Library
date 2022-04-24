package com.vicras.blog.exception.exceptions.security;

public class ForbiddenException extends SecurityException {
    public ForbiddenException(String message) {
        super(message);
    }
}
