package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.step.MatchStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchService {

    private final MatchStep matchStep;

    public MatchService(@Qualifier("checkStep") MatchStep matchStep) {
        this.matchStep = matchStep;
    }

    public void enter(MatchRequest matchRequest, String user, Long memberId) {
        MatchingContext matchingContext = new MatchingContext(matchRequest, user, memberId);
        matchStep.process(matchingContext);
    }
}

