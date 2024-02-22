package com.quiet.onterview.room.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class RoomNotFoundException extends BaseException {

    public RoomNotFoundException() {
        super(ErrorCode.ROOM_NOT_FOUND);
    }
}
