package com.vicras.blog.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vicras.blog.constants.TimeConstants;
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

    @JsonFormat(pattern = TimeConstants.ERROR_DATE_TIME_FORMAT)
    private OffsetDateTime timeStamp;
}
