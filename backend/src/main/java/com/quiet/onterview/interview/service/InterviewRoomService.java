package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.dto.request.InterviewRoomDeleteRequest;
import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.*;
import com.quiet.onterview.interview.entity.RoomType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InterviewRoomService {

    Page<InterviewRoomResponse> getInterviewRoomList(Long memberId, RoomType roomType,
            Pageable pageable);

    InterviewRoomDetailResponse getInterviewRoomDetail(Long memberId, Long interviewRoomId);

    List<List<InterviewQuestionCreateResponse>> createInterviewRoom(
            InterviewRoomRequest interviewRoomRequest);

    void deleteInterviewRoom(Long memberId, String token,
            InterviewRoomDeleteRequest interviewRoomDeleteRequest);
}