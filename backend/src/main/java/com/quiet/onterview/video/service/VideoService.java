package com.quiet.onterview.video.service;

import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.video.dto.request.*;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import java.util.List;

public interface VideoService {

    VideoDetailResponse loadVideoInformation(Long videoId);

    List<VideoInformationResponse> loadAllMyVideo(SecurityUser email, RoomType roomType,
            String keyword, Integer bookmark);

    void createVideoInformation(VideoInformationRequest videoInformationRequest);

    void updateVideo(Long videoId, VideoUpdateRequest videoUpdateRequest);

    void deleteVideo(VideoDeleteRequest videos, String token);
}
