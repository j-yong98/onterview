package com.quiet.onterview.websocket.handler;

import com.quiet.onterview.websocket.StompUser;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebsocketConnectHandShakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String stompId = UUID.randomUUID().toString();
        log.info("handshake sessionId : {}", stompId);
        return new StompUser(stompId);
    }

}
