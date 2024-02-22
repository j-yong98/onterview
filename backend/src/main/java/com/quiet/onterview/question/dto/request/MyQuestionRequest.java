package com.quiet.onterview.question.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyQuestionRequest {

    private Long myQuestionFolderId;
    private String question;
    private Long commonQuestionId;
}
