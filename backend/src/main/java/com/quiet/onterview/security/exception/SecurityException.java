package com.quiet.onterview.security.exception;

import com.quiet.onterview.common.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class SecurityException extends AuthenticationException {

    private ErrorCode errorCode;
    HttpStatus httpStatus;
    String message;

    public SecurityException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
    }
}
