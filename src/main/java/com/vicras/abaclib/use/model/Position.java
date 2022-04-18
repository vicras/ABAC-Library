package com.vicras.abaclib.use.model;

import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter(onMethod_ = {@JsonValue})
@RequiredArgsConstructor
public enum Position {
    MANAGER("Менеджер"),
    CEO("Управляющий"),
    ACCOUNTANT("Наблюдатель");

    private final String title;

    public static Optional<Position> findByTitle(String expression) {
        return Arrays.stream(Position.values())
                .filter(fieldName -> fieldName.title.equalsIgnoreCase(expression))
                .findFirst();
    }

    @JsonCreator
    public static Position getByTitle(String expression) {
        return Position.findByTitle(expression)
                .orElseThrow(() -> new IllegalArgumentException(format(
                        "Тип позиции %s не найден",
                        expression)));
    }
}