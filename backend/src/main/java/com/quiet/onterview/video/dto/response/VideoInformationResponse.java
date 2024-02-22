package com.quiet.onterview.video.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VideoInformationResponse {

    private Long videoId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long myQuestionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long interviewQuestionId;
    private String title;
    private String question;
    private FileInformationResponse thumbnailUrl;
    private Boolean bookmark;

    @Builder
    public VideoInformationResponse(Long videoId, Long myQuestionId, Long interviewQuestionId,
            String title, FileInformationResponse thumbnailUrl, Boolean bookmark) {
        this.videoId = videoId;
        this.myQuestionId = myQuestionId;
        this.interviewQuestionId = interviewQuestionId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.bookmark = bookmark;
    }

    public VideoInformationResponse(Long videoId, Long interviewQuestionId, String title,
            FileInformationResponse thumbnailUrl, Boolean bookmark) {
        this.videoId = videoId;
        this.interviewQuestionId = interviewQuestionId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.bookmark = bookmark;
    }
}
