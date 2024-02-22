package com.quiet.onterview.matching.step;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.entity.QuestionType;
import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.interview.service.InterviewRoomService;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.room.service.RoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AfterMatchStep implements MatchStep {

    private InterviewRoomService interviewRoomService;
    private RoomService roomService;
    private MatchStep matchStep;

    public AfterMatchStep(
            RoomService roomService,
            @Qualifier("announceStep") MatchStep matchStep,
            InterviewRoomService interviewRoomService
    ) {
        this.roomService = roomService;
        this.matchStep = matchStep;
        this.interviewRoomService = interviewRoomService;
    }

    @Override
    public void process(MatchingContext matchingContext) {
        List<MatchUser> matchUsers = matchingContext.getMatchUsers();
        matchingContext.setQuestions(interviewRoomService.createInterviewRoom(InterviewRoomRequest.builder()
                .roomType(RoomType.MULTI)
                .questionType(intToQuestionType(matchingContext.getRoomId()))
                .numToSelect(5)
                .memberIdList(matchUsers.stream().map(MatchUser::getMemberId).toList())
                .build()));
        roomService.generate(matchingContext.getSessionId(), matchUsers, matchingContext.getQuestions());
        matchStep.process(matchingContext);
    }

    private QuestionType intToQuestionType(Integer type) {
        return QuestionType.values()[type - 1];
    }
}
