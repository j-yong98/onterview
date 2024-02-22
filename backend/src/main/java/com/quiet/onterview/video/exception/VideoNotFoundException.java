package com.quiet.onterview.video.exception;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;

public class VideoNotFoundException extends BaseException {
    public VideoNotFoundException() {
        super(ErrorCode.VIDEO_NOT_FOUND);
    }
}
