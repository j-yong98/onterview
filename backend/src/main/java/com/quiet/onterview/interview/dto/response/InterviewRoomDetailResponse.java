package com.quiet.onterview.interview.dto.response;

import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InterviewRoomDetailResponse {

    private Long interviewRoomId;
    private String questionType;
    private RoomType roomType;
    private Integer runTime;
    private String createAt;
    private String feedback;
    private List<InterviewQuestionResponse> interviewQuestionList;
    private VideoDetailResponse videoDetail;
}