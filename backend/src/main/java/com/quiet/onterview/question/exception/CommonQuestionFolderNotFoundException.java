package com.quiet.onterview.question.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class CommonQuestionFolderNotFoundException extends BaseException {
    public CommonQuestionFolderNotFoundException() {
        super(ErrorCode.COMMON_QUESTION_FOLDER_NOT_FOUND);
    }
}
