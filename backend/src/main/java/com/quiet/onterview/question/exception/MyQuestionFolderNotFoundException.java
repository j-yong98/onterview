package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class MyQuestionFolderNotFoundException extends BaseException {
    public MyQuestionFolderNotFoundException() {
        super(ErrorCode.MY_QUESTION_FOLDER_NOT_FOUND);
    }
}
