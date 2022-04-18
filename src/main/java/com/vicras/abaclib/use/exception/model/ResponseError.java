package com.vicras.abaclib.use.exception.model;

import static com.vicras.abaclib.use.constants.TimeConstants.ERROR_DATE_TIME_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    private Integer errorCode;
    private String errorMessage;

    @JsonFormat(pattern = ERROR_DATE_TIME_FORMAT)
    private OffsetDateTime timeStamp;
}
