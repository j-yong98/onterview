package com.quiet.onterview.matching.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class TypeMismatchException extends BaseException {

    public TypeMismatchException() {
        super(ErrorCode.TYPE_MISMATCH);
    }
}
