package com.quiet.onterview.matching.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class CanNotWriteException extends BaseException {

    public CanNotWriteException() {
        super(ErrorCode.CAN_NOT_WRITE);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
