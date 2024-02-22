package com.quiet.onterview.interview.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class InterviewQuestionNotFoundException extends BaseException {

    public InterviewQuestionNotFoundException() {
        super(ErrorCode.INTERVIEW_QUESTION_NOT_FOUND);
    }
}
