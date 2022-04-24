package com.vicras.blog.exception.handler;

import com.vicras.blog.exception.exceptions.AbacUseException;
import com.vicras.blog.exception.handler.custom.CustomExceptionsHandler;
import com.vicras.blog.exception.handler.system.ExceptionsHandler;
import com.vicras.blog.exception.model.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ExceptionsHandler handlerFactory;
    private final CustomExceptionsHandler customExceptionsFactory;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exceptionHandler(Exception exception) {
        log.warn(exception.getMessage(), exception);
        return handlerFactory.handle(exception);
    }

    @ExceptionHandler(AbacUseException.class)
    public ResponseEntity<ResponseError> exceptionHandler(AbacUseException exception) {
        log.warn(exception.getMessage(), exception);
        return customExceptionsFactory.handle(exception);
    }
}