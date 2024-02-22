package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommonQuestionFolderResponse {

    private Long commonQuestionFolderId;
    private String commonQuestionFolder;
    private List<CommonQuestionResponse> commonQuestionList;
}
