package com.vicras.blog.exception.handler.custom;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.vicras.blog.constants.ErrorConstants;
import com.vicras.blog.exception.exceptions.business.EntityNotFoundException;
import com.vicras.blog.exception.exceptions.internal.InternalException;
import com.vicras.blog.exception.exceptions.security.ForbiddenException;
import com.vicras.blog.exception.handler.BaseExceptionHandler;
import com.vicras.blog.exception.model.ResponseError;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionsHandler extends BaseExceptionHandler {

    @Getter
    private final Map<Class<? extends Exception>, Function<Exception, ResponseEntity<ResponseError>>> handleExceptionMap = Map.of(
            EntityNotFoundException.class,
            exc -> handleEntityNotFoundException((EntityNotFoundException) exc),
            InternalException.class,
            this::internalException,
            ForbiddenException.class,
            this::forbiddenException
    );

    private ResponseEntity<ResponseError> forbiddenException(Exception e) {
        return buildResponseError(e.getMessage(), FORBIDDEN);
    }

    private ResponseEntity<ResponseError> handleEntityNotFoundException(EntityNotFoundException exc) {
        return buildResponseError(exc.getMessage(), NOT_FOUND);
    }

    private ResponseEntity<ResponseError> internalException(Exception e) {
        return buildResponseError(
                String.format("%s, %s", e.getMessage(), ErrorConstants.CONTACT_WITH_ADMINISTRATOR),
                INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<ResponseError> handleDefaultException(Exception exc) {
        return buildResponseError(exc.getMessage(), BAD_REQUEST);
    }
}
