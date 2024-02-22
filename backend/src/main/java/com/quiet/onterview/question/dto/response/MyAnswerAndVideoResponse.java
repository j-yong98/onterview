package com.quiet.onterview.question.dto.response;

import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyAnswerAndVideoResponse {

    private Long myQuestionId;
    private String question;
    private String answer;
    private List<VideoInformationResponse> videoInformationResponseList;
}
