package com.quiet.onterview.interview.dto.response;

import com.quiet.onterview.interview.entity.RoomType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InterviewRoomResponse {

    private Long interviewRoomId;
    private String questionType;
    private RoomType roomType;
    private Integer runTime;
    private String createAt;
}