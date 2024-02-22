package com.quiet.onterview.question.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyQuestionMoveRequest {

    private Long myQuestionId;
    private Long fromMyQuestionFolderId;
    private Long toMyQuestionFolderId;
}
