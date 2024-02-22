package com.quiet.onterviewstorage.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseExceptionResponse {

    private final int status;
    private final String message;
}
