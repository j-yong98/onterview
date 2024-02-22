package com.quiet.onterview.websocket.handler;

import com.quiet.onterview.matching.exception.UserNotFoundException;
import com.quiet.onterview.matching.repository.MatchRepository;
import com.quiet.onterview.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {

    private final MatchRepository matchRepository;
    private final RoomService roomService;
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String user = event.getUser().getName();
        try {
            roomService.leave(user);
        } catch (UserNotFoundException e) {
            matchRepository.leave(user);
        }
        log.info("disconnect sessionId : {}", user);
    }
}
