package com.quiet.onterview.room.dto.request;

import com.quiet.onterview.file.dto.request.FileInformationRequest;
import lombok.Getter;

@Getter
public class UserVideoRequestMessage {
    private String title;
    private Long videoLength;
    private FileInformationRequest videoUrl;
    private FileInformationRequest thumbnailUrl;
}
