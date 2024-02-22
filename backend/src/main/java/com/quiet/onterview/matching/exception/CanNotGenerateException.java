package com.quiet.onterview.matching.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class CanNotGenerateException extends BaseException {

    public CanNotGenerateException() {
        super(ErrorCode.CAN_NOT_GENERATE);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
