package com.vicras.abaclib.use.exception.handler.custom;

import static com.vicras.abaclib.use.constants.ErrorConstants.CONTACT_WITH_ADMINISTRATOR;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.vicras.abaclib.use.exception.exceptions.business.EntityNotFoundException;
import com.vicras.abaclib.use.exception.exceptions.internal.InternalException;
import com.vicras.abaclib.use.exception.exceptions.security.ForbiddenException;
import com.vicras.abaclib.use.exception.handler.BaseExceptionHandler;
import com.vicras.abaclib.use.exception.model.ResponseError;
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
                format("%s, %s", e.getMessage(), CONTACT_WITH_ADMINISTRATOR),
                INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<ResponseError> handleDefaultException(Exception exc) {
        return buildResponseError(exc.getMessage(), BAD_REQUEST);
    }
}
