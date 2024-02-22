package com.quiet.onterview.video.dto.request;

import com.quiet.onterview.file.dto.request.FileInformationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoInformationRequest {

    private Long myQuestionId;
    private Long interviewQuestionId;
    private Long videoLength;
    private String title;
    private FileInformationRequest videoInformation;
    private FileInformationRequest thumbnailInformation;
}
