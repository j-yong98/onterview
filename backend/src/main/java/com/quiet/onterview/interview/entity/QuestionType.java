package com.quiet.onterview.interview.entity;

import lombok.Getter;

@Getter
public enum QuestionType {
    FIT("인성 면접"),
    BACKEND("백엔드 직무면접"),
    FRONTEND("프론트엔드 직무면접");

    private final String commonQuestionFolder;

    QuestionType(String commonQuestionFolder) {
        this.commonQuestionFolder = commonQuestionFolder;
    }
}