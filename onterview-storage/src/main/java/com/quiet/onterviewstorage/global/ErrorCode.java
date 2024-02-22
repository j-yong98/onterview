package com.quiet.onterviewstorage.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    CHUNK_NOT_FOUND(HttpStatus.NOT_FOUND, "청크를 찾을 수 없습니다."),
    FILE_MUST_VIDEO(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 타입의 파일입니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;
}
