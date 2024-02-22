package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class InvalidSelectionCountException extends BaseException {
    public InvalidSelectionCountException() {
        super(ErrorCode.INVALID_SELECTION_COUNT);
    }
}
