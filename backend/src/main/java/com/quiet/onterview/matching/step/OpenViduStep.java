package com.quiet.onterview.matching.step;

import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.exception.CanNotGenerateException;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties.Builder;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenViduStep implements MatchStep {

    @Value("${openvidu.hostname}")
    private String hostname;
    @Value("${openvidu.secret}")
    private String secret;

    private OpenVidu openVidu;
    private MatchStep next;

    public OpenViduStep(@Qualifier("afterMatchStep") MatchStep matchStep) {
        this.next = matchStep;
    }

    @PostConstruct
    private void init() {
        openVidu = new OpenVidu(hostname, secret);
    }

    @Override
    public void process(MatchingContext matchingContext) {
        matchingContext.setSessionId(makeSessionId());
        String sessionId = matchingContext.getSessionId();
        List<MatchUser> matchUsers = matchingContext.getMatchUsers();
        try {
            Session activeSession = makeSession(sessionId);
            for (MatchUser matchUser : matchUsers) {
                matchUser.setToken(makeToken(activeSession));
            }
            next.process(matchingContext);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new CanNotGenerateException();
        }
    }

    private Session makeSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        return Optional.ofNullable(openVidu.getActiveSession(sessionId))
                .orElse(openVidu.createSession(new Builder().customSessionId(sessionId).build()));
    }

    private String makeToken(Session session) throws OpenViduJavaClientException, OpenViduHttpException {
        return session.createConnection().getToken();
    }

    private String makeSessionId() {
        return UUID.randomUUID().toString();
    }
}
