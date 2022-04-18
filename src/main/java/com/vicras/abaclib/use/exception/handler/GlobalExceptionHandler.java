package com.vicras.abaclib.use.exception.handler;

import com.vicras.abaclib.use.exception.exceptions.AbacUseException;
import com.vicras.abaclib.use.exception.handler.custom.CustomExceptionsHandler;
import com.vicras.abaclib.use.exception.handler.system.ExceptionsHandler;
import com.vicras.abaclib.use.exception.model.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.w3c.dom.events.EventException;


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
    public ResponseEntity<ResponseError> exceptionHandler(EventException exception) {
        log.warn(exception.getMessage(), exception);
        return customExceptionsFactory.handle(exception);
    }
}