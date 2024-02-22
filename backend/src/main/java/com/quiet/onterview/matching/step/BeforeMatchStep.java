package com.quiet.onterview.matching.step;

import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeforeMatchStep implements MatchStep{


    private final MatchRepository matchRepository;
    private final MatchStep next;

    public BeforeMatchStep(@Qualifier("matchingStep") MatchStep matchStep, MatchRepository matchRepository) {
        this.next = matchStep;
        this.matchRepository = matchRepository;
    }

    @Override
    public void process(MatchingContext matchingContext) {
        if (!isExist(matchingContext)) {
            matchRepository.enter(matchingContext.getRoomId(), matchingContext.getMatchUser());
        }
        next.process(matchingContext);
    }

    private boolean isExist(MatchingContext matchingContext) {
        return matchRepository.isExist(matchingContext.getRoomId(), matchingContext.getMatchUser());
    }
}
