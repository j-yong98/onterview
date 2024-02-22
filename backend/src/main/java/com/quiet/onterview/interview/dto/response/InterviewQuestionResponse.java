package com.quiet.onterview.interview.dto.response;

import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InterviewQuestionResponse {

    private Long interviewQuestionId;
    private Long commonQuestionId;
    private String commonQuestion;
    private VideoInformationResponse videoInformation;

}