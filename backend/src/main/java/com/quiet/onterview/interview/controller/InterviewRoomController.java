package com.quiet.onterview.interview.controller;

import com.quiet.onterview.interview.dto.request.InterviewRoomDeleteRequest;
import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.*;
import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.interview.service.InterviewRoomService;
import com.quiet.onterview.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "interview-room-controller", description = "모의 면접장 컨트롤러")
@RestController
@RequestMapping("/api/interview-room")
@RequiredArgsConstructor
public class InterviewRoomController {

    private final InterviewRoomService interviewRoomService;

    @Operation(summary = "GET 방식으로 1인/다인 모의 면접장 전체 조회")
    @GetMapping
    public ResponseEntity<Page<InterviewRoomResponse>> getInterviewRoomList(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam(name = "roomType", required = true) RoomType roomType,
            @PageableDefault(size = 10, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("interview-room request : {}, {}", user.toString(), roomType);
        Page<InterviewRoomResponse> interviewRoomList = interviewRoomService.getInterviewRoomList(
                user.getMemberId(), roomType, pageable);
        log.info("result size : {}", interviewRoomList.getSize());
        return ResponseEntity.ok(interviewRoomList);
    }

    @Operation(summary = "GET 방식으로 특정 모의 면접장 상세 조회")
    @GetMapping("/{interview_room_id}")
    public ResponseEntity<InterviewRoomDetailResponse> getInterviewRoomDetail(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("interview_room_id") Long interviewRoomId) {
        return ResponseEntity.ok(
                interviewRoomService.getInterviewRoomDetail(user.getMemberId(), interviewRoomId));
    }

    @Operation(summary = "POST 방식으로 1인 모의 면접장 생성")
    @PostMapping
    public ResponseEntity<List<List<InterviewQuestionCreateResponse>>> registerInterviewRoom(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody InterviewRoomRequest interviewRoomRequest) {
        interviewRoomRequest.getMemberIdList().add(user.getMemberId());
        return ResponseEntity.ok(interviewRoomService.createInterviewRoom(interviewRoomRequest));
    }

    @Operation(summary = "POST 방식으로 다인 모의 면접장 생성 테스트용")
    @PostMapping("/multiTest")
    public ResponseEntity<List<List<InterviewQuestionCreateResponse>>> registerMultiInterviewRoom(
            @RequestBody InterviewRoomRequest interviewRoomRequest) {
        return ResponseEntity.ok(interviewRoomService.createInterviewRoom(interviewRoomRequest));
    }

    @Operation(summary = "DELETE 방식으로 모의 면접장 삭제")
    @PostMapping("/delete")
    public ResponseEntity<List<Long>> deleteInterviewRoom(
            @AuthenticationPrincipal SecurityUser user,
            @RequestHeader("Authorization") String token,
            @RequestBody InterviewRoomDeleteRequest interviewRoomDeleteRequest) {
        interviewRoomService.deleteInterviewRoom(user.getMemberId(), token,
                interviewRoomDeleteRequest);
        return ResponseEntity.ok(interviewRoomDeleteRequest.interviewRoomIdList);
    }
}
