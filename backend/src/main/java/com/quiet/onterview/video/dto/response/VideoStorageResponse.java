package com.quiet.onterview.video.dto.response;

import com.quiet.onterview.file.entity.FileInformation;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoStorageResponse {

    private Long videoId;
    private FileInformation thumbnailUrl;
    private String title;
    private String question;
}