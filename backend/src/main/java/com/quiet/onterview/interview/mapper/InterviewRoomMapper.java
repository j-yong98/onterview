package com.quiet.onterview.interview.mapper;

import static com.quiet.onterview.utils.DateFormatter.interviewRoomDateFormatter;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.*;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.mapper.VideoMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewRoomMapper {

    private final InterviewQuestionMapper interviewQuestionMapper;
    private final VideoMapper videoMapper;

    public InterviewRoom interviewRoomRequestToEntity(InterviewRoomRequest interviewRoomRequest) {
        return InterviewRoom.builder()
                .questionType(interviewRoomRequest.getQuestionType())
                .roomType(interviewRoomRequest.getRoomType())
                .intervieweeList(new ArrayList<>())
                .build();
    }

    public InterviewRoomResponse interviewRoomTointerviewRoomResponse(InterviewRoom interviewRoom) {
        return InterviewRoomResponse.builder()
                .interviewRoomId(interviewRoom.getInterviewRoomId())
                .runTime(interviewRoom.getRuntime())
                .questionType(interviewRoom.getQuestionType().getCommonQuestionFolder())
                .roomType(interviewRoom.getRoomType())
                .createAt(interviewRoom.getCreateAt().format(interviewRoomDateFormatter))
                .build();
    }

    public InterviewRoomDetailResponse interviewRoomToInterviewRoomDetailResponse(
            List<InterviewQuestion> interviewQuestionList, InterviewRoom interviewRoom) {
        List<InterviewQuestionResponse> interviewQuestionResponseList = interviewQuestionList.stream()
                .filter(interviewQuestion -> interviewQuestion.getVideo() != null)
                .map(interviewQuestionMapper::interviewQuestionToInterviewQuestionResponse)
                .toList();

        InterviewQuestion firstInterviewQuestion = interviewQuestionList.get(0);
        Video video = firstInterviewQuestion.getVideo();
        VideoDetailResponse videoDetailResponse =
                (video != null) ? videoMapper.videoToDetailResponse(video) : null;

        return InterviewRoomDetailResponse.builder()
                .interviewRoomId(interviewRoom.getInterviewRoomId())
                .questionType(interviewRoom.getQuestionType().getCommonQuestionFolder())
                .roomType(interviewRoom.getRoomType())
                .runTime(interviewRoom.getRuntime())
                .createAt(interviewRoom.getCreateAt().format(interviewRoomDateFormatter))
                .interviewQuestionList(interviewQuestionResponseList)
                .videoDetail(videoDetailResponse)
                .build();
    }
}