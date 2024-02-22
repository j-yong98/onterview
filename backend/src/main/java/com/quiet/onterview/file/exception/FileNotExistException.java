package com.quiet.onterview.file.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class FileNotExistException extends BaseException {

    public FileNotExistException() {
        super(ErrorCode.FILE_NOT_EXIST);
    }
}
