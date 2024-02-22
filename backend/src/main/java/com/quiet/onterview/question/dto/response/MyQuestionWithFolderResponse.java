package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyQuestionWithFolderResponse {

    private Long myQuestionFolderId;
    private Long myQuestionId;
    private String question;
}
