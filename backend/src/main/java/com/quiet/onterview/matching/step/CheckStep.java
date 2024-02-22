package com.quiet.onterview.matching.step;

import static com.quiet.onterview.matching.MatchStatus.CHECK;

import com.quiet.onterview.matching.MatchStatus;
import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.dto.response.MatchCheckResponse;
import com.quiet.onterview.websocket.MessageAnnounce;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CheckStep implements MatchStep {

    private final String SUB_HEADER = "/sub/";
    private final MessageAnnounce messageAnnounce;
    private final MatchStep next;

    public CheckStep(MessageAnnounce messageAnnounce,
            @Qualifier("beforeMatchStep") MatchStep next) {
        this.messageAnnounce = messageAnnounce;
        this.next = next;
    }

    @Override
    public void process(MatchingContext matchingContext) {
        MatchStatus status = matchingContext.getStatus();
        if (status == null) {
            next.process(matchingContext);
            return;
        }
        messageAnnounce.announceToUser(
                SUB_HEADER + matchingContext.getRoomId(),
                matchingContext.getMatchUser().getPrincipal(),
                MatchCheckResponse.builder().status(CHECK).build()
        );
    }
}
