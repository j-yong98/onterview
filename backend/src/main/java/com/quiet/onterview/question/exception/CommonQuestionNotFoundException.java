package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class CommonQuestionNotFoundException extends BaseException {
    public CommonQuestionNotFoundException() {
        super(ErrorCode.COMMON_QUESTION_NOT_FOUND);
    }
}
