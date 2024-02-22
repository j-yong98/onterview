package com.quiet.onterview.video.controller;

import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.video.dto.request.*;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.service.VideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/upload")
    public ResponseEntity<Void> getUpload() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDetailResponse> getVideoInformation(@PathVariable Long videoId) {
        return ResponseEntity.ok(videoService.loadVideoInformation(videoId));
    }

    @GetMapping
    public ResponseEntity<List<VideoInformationResponse>> getAllMyVideoByCategory(
            @RequestParam(name = "category") RoomType roomType,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "bookmark", required = false) Integer bookmark,
            @AuthenticationPrincipal SecurityUser user) {
        log.info("Video Request : {}, {}, {}, {}", user.toString(), roomType, keyword, bookmark);
        List<VideoInformationResponse> videoInformationResponses = videoService.loadAllMyVideo(user,
                roomType, keyword, bookmark);
        log.info("result - size : {}", videoInformationResponses.size());
        return ResponseEntity.ok(videoInformationResponses);
    }

    @PostMapping
    public ResponseEntity<Void> registerVideo(
            @RequestBody VideoInformationRequest videoInformationRequest) {
        videoService.createVideoInformation(videoInformationRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{videoId}")
    public ResponseEntity<Void> updateVideo(
            @PathVariable Long videoId,
            @RequestBody VideoUpdateRequest videoUpdateRequest
    ) {
        videoService.updateVideo(videoId, videoUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteVideo(@RequestBody VideoDeleteRequest videos,
            @RequestHeader(name = "Authorization") String token) {
        videoService.deleteVideo(videos, token);
        return ResponseEntity.ok().build();
    }
}
