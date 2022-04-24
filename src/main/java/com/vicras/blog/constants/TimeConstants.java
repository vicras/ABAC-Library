package com.vicras.blog.constants;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimeConstants {
    // 2021-12-17T09:37:06
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            DEFAULT_DATE_FORMAT);

    public static final String EVENT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateTimeFormatter EVENT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            EVENT_DATE_TIME_FORMAT);

    public static final String ERROR_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}