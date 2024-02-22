package com.quiet.onterview.video.dto.response;

import com.quiet.onterview.file.dto.response.FileInformationResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoDetailResponse {

    private Long videoId;
    private Long myQuestionId;
    private Long interviewQuestionId;
    private String title;
    private FileInformationResponse videoUrl;
    private String feedback;
    private Boolean bookmark;
}
