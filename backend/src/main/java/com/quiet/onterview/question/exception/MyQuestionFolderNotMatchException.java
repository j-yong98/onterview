package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class MyQuestionFolderNotMatchException extends BaseException {
    public MyQuestionFolderNotMatchException() {
        super(ErrorCode.MY_QUESTION_FOLDER_NOT_MATCH);
    }
}
