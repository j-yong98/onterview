package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonQuestionWithFolderResponse {

    private Long commonQuestionFolderId;
    private String commonQuestionFolder;
    private Long commonQuestionId;
    private String CommonQuestion;
}
