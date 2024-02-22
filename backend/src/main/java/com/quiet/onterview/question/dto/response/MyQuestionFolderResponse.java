package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyQuestionFolderResponse {

    private Long myQuestionFolderId;
    private String myQuestionFolder;
    private List<MyQuestionResponse> myQuestionList;
}
