package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class MyQuestionNotFoundException extends BaseException {
    public MyQuestionNotFoundException() {
        super(ErrorCode.MY_QUESTION_NOT_FOUND);
    }
}
