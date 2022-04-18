package com.vicras.abaclib.use.exception.handler.system;

import static com.vicras.abaclib.use.constants.ErrorConstants.INVALID_REQUEST_ERROR_MSG;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.vicras.abaclib.use.exception.handler.BaseExceptionHandler;
import com.vicras.abaclib.use.exception.model.ResponseError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
@RequiredArgsConstructor
public class ExceptionsHandler extends BaseExceptionHandler {

    private final ArgumentNotValidResolver argumentNotValidResolver;

    @Getter
    private final Map<Class<? extends Exception>, Function<Exception, ResponseEntity<ResponseError>>> handleExceptionMap = Map.of(
            MethodArgumentNotValidException.class,
            exc -> handleMethodArgumentNotValidException((MethodArgumentNotValidException) exc),
            HttpMessageNotReadableException.class,
            exc -> handleMessageNotReadable((HttpMessageNotReadableException) exc)
    );

    // Json parse error
    private ResponseEntity<ResponseError> handleMessageNotReadable(HttpMessageNotReadableException exc) {
        return buildResponseError(INVALID_REQUEST_ERROR_MSG, BAD_REQUEST);
    }

    // Validation exceptions
    private ResponseEntity<ResponseError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        return buildResponseError(argumentNotValidResolver.resolve(ex), BAD_REQUEST);
    }
}