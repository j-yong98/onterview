package com.quiet.onterview.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * MEMBER
     */
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "이메일이 중복되었습니다"),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "닉네임이 중복되었습니다"),
    PASSWORD_CANNOT_CONFIRM(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
    PASSWORD_NOT_MATCHES(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    EMAIL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 이메일을 가진 유저가 없습니다"),
    MEMBERID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 아이디를 가진 유저가 없습니다"),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.BAD_REQUEST, "ACCESS TOKEN이 만료되지 않았습니다"),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "ACCESS TOKEN이 만료되었습니다"),
    ACCESS_TOKEN_NOT_RECEIVED(HttpStatus.UNAUTHORIZED, "ACCESS TOKEN이 없습니다"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "REFRESH TOKEN이 만료되었습니다"),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다"),

    /**
     * Question
     */
    MY_QUESTION_FOLDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MY_QUESTION_FOLDER_NOT_FOUND"),
    MY_QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MY_QUESTION_NOT_FOUND"),
    MY_QUESTION_FOLDER_NOT_MATCH(HttpStatus.BAD_REQUEST, "MY_QUESTION_FOLDER_NOT_MATCH"),
    COMMON_QUESTION_FOLDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON_QUESTION_FOLDER_NOT_FOUND"),
    COMMON_QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMON_QUESTION_NOT_FOUND"),
    QUESTION_FOLDER_EMPTY(HttpStatus.BAD_REQUEST, "QUESTION_FOLDER_EMPTY"),
    INVALID_SELECTION_COUNT(HttpStatus.BAD_REQUEST, "INVALID_SELECTION_COUNT"),
    INTERVIEW_QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "INTERVIEW_QUESTION_NOT_FOUND"),
    USER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "NOT_FOUND_USER"),

    /**
     * Interviewee
     */
    INTERVIEWEE_NOT_FOUND(HttpStatus.NOT_FOUND, "INTERVIEWEE_NOT_FOUND"),

    /**
     * InterviewRoom
     */
    INTERVIEW_ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "INTERVIEW_ROOM_NOT_FOUND"),

    /**
     * Video
     */
    VIDEO_NOT_FOUND(HttpStatus.BAD_REQUEST, "VIDEO_NOT_FOUND"),

    /**
     * File
     */
    FILE_NOT_EXIST(HttpStatus.BAD_REQUEST, "FILE_NOT_EXIST"),

    /**
     * Matching
     */
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "MESSAGE_TYPE_MISMATCH"),
    CAN_NOT_GENERATE(HttpStatus.INTERNAL_SERVER_ERROR, "CAN NOT GENERATE"),
    CAN_NOT_WRITE(HttpStatus.INTERNAL_SERVER_ERROR, "CAN NOT WRITE"),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "NOT FOUND ROOM"),

    /**
     * COMMUNITY
     */
    ARTICLE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다"),
    ARTICLE_WRITER_NOT_MATCHES(HttpStatus.BAD_REQUEST, "자신이 작성한 게시글이 아닙니다"),

    COMMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 댓글이 존재하지 않습니다"),
    CANNOT_CREATE_CHILD_COMMENT_TO_CHILD_COMMENT(HttpStatus.BAD_REQUEST, "답글에는 답글을 달 수 없습니다"),
    COMMENT_WRITER_NOT_MATCHES(HttpStatus.BAD_REQUEST, "자신이 작성한 피드백/답글이 아닙니다"),

    /**
     * COMMON
     */

    REQUIRED_VALUE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "필요한 값이 없습니다"),
    REQUEST_CONDITION_NOT_MATCHES(HttpStatus.BAD_REQUEST, "요청 조건이 맞지 않습니다");

    HttpStatus statusCode;
    String message;

}
