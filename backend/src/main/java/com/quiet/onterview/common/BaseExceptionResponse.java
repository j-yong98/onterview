package com.quiet.onterview.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseExceptionResponse {

    int errorCode;
    String errorMessage;
}
