package com.vicras.blog.exception.handler.system;


import static java.lang.Boolean.FALSE;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vicras.blog.constants.ErrorConstants;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
@RequiredArgsConstructor
public class ArgumentNotValidResolver {

    private final TextChecker textChecker;

    public String resolve(MethodArgumentNotValidException exception) {
        return resolveGlobalErrors(exception.getGlobalErrors()).orElse(EMPTY) + " " +
                resolveFieldErrors(exception.getFieldErrors()).orElse(EMPTY);
    }

    private Optional<String> resolveGlobalErrors(List<ObjectError> globalErrors) {
        final String globalErrorMessage = globalErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .map(text -> hasCustomMessage(text)
                        ? text
                        : ErrorConstants.INVALID_REQUEST_ERROR_MSG)
                .distinct()
                .collect(Collectors.joining(", "));
        return emptyIfTrue(globalErrorMessage, String::isEmpty)
                .map(errors -> ErrorConstants.GLOBAL_ERRORS + errors);
    }

    private Optional<String> resolveFieldErrors(List<FieldError> fieldErrors) {
        var groupedByFieldErrors = fieldErrors.stream()
                .collect(Collectors.groupingBy(FieldError::getField, toList()));
        return emptyIfTrue(addNewErrorMessage(groupedByFieldErrors), Map::isEmpty)
                .map(error -> ErrorConstants.FIELD_ERRORS + error);
    }

    public Map<String, String> addNewErrorMessage(Map<String, List<FieldError>> errors) {
        return errors.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> getErrorMessageForSameFields(entry.getValue())));
    }

    public String getErrorMessageForSameFields(List<FieldError> sameField) {
        return sameField.stream()
                .filter(this::hasCustomMessage)
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(ErrorConstants.FIELD_FORMAT_ERROR);
    }

    private boolean hasCustomMessage(FieldError fieldError) {
        return hasCustomMessage(fieldError.getDefaultMessage());
    }

    private Boolean hasCustomMessage(String text) {
        return ofNullable(text)
                .map(textChecker::containsCyrillic)
                .orElse(FALSE);
    }

    private <T> Optional<T> emptyIfTrue(T t, Predicate<T> predicate) {
        return predicate.test(t) ? empty() : ofNullable(t);
    }
}
