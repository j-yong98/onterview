package com.quiet.onterview.matching;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.matching.dto.request.MatchRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MatchingContext {

    private final MatchStatus status;
    private final Integer roomId;
    private final MatchUser matchUser;
    private final Integer matchCount;

    @Setter
    private String sessionId;
    @Setter
    private List<MatchUser> matchUsers;
    @Setter
    private List<List<InterviewQuestionCreateResponse>> questions;

    public MatchingContext(MatchRequest matchRequest, String principal, Long memberId) {
        this.status = matchRequest.getStatus();
        this.roomId = matchRequest.getRoomId();
        this.matchUser = MatchUser.builder().memberId(memberId).principal(principal).build();
        this.matchCount = matchRequest.getMatchCount();
        this.sessionId = null;
        this.matchUsers = null;
    }
}
