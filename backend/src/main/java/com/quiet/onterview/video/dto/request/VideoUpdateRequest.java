package com.quiet.onterview.video.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUpdateRequest {

    String title;
    String feedback;
    Boolean bookmark;
}
