package com.quiet.onterview.interview.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class InterviewRoomNotFoundException extends BaseException {
    public InterviewRoomNotFoundException() {
            super(ErrorCode.INTERVIEW_ROOM_NOT_FOUND);
    }
}
