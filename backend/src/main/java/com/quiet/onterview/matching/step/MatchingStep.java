package com.quiet.onterview.matching.step;

import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MatchingStep implements MatchStep {

    private final MatchRepository matchRepository;
    private final MatchStep next;

    public MatchingStep(MatchRepository matchRepository, @Qualifier("openViduStep") MatchStep matchStep) {
        this.matchRepository = matchRepository;
        this.next = matchStep;
    }

    @Override
    public void process(MatchingContext matchingContext) {
        if (matchRepository.isMatch(matchingContext.getRoomId(), matchingContext.getMatchCount())) {
            matchingContext.setMatchUsers(
                    matchRepository.getUsers(matchingContext.getRoomId(), matchingContext.getMatchCount()));

            next.process(matchingContext);
        }
    }
}
