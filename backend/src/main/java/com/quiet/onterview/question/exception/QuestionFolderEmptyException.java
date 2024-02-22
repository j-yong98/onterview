package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class QuestionFolderEmptyException extends BaseException {
    public QuestionFolderEmptyException() {
        super(ErrorCode.QUESTION_FOLDER_EMPTY);
    }
}
