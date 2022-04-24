package com.vicras.blog.exception.exceptions.security;

public class AuthenticationException extends SecurityException {
    public AuthenticationException(String incorrect_password_or_username) {
        super(incorrect_password_or_username);
    }
}
