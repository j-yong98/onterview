package com.quiet.onterview.matching.step;

import com.quiet.onterview.interview.exception.InterviewQuestionNotFoundException;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.dto.response.MatchResultResponse;
import com.quiet.onterview.room.exception.RoomNotFoundException;
import com.quiet.onterview.websocket.MessageAnnounce;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnnounceStep implements MatchStep {

    private final String SUB_HEADER = "/sub/";
    private final MessageAnnounce messageAnnounce;

    @Override
    public void process(MatchingContext matchingContext) {
        List<MatchUser> matchUsers = matchingContext.getMatchUsers();
        AtomicInteger index = new AtomicInteger(0);
        matchUsers.forEach(matchUser -> messageAnnounce.announceToUser(
                SUB_HEADER + matchingContext.getRoomId(),
                matchUser.getPrincipal(),
                MatchResultResponse.builder()
                        .roomId(matchingContext.getQuestions()
                                .stream()
                                .findFirst().orElseThrow(RoomNotFoundException::new)
                                .stream()
                                .findFirst().orElseThrow(
                                InterviewQuestionNotFoundException::new)
                                .getRoomId())
                        .sessionId(matchingContext.getSessionId())
                        .token(matchUser.getToken())
                        .index(index.getAndIncrement()).build())
        );
    }
}
