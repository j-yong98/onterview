package com.quiet.onterview.question.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonQuestionResponse {

    private Long commonQuestionId;
    private String CommonQuestion;
}
