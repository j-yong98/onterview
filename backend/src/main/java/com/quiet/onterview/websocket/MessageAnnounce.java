package com.quiet.onterview.websocket;

import com.quiet.onterview.websocket.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageAnnounce {

    private final MessageMapper messageMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void announceAll(String target, Object object) {
        String message = messageMapper.responseToJson(object);
        simpMessagingTemplate.convertAndSend(target, message);
    }

    public void announceToUser(String target, String user, Object object) {
        String message = messageMapper.responseToJson(object);
        simpMessagingTemplate.convertAndSendToUser(user, target, message);
    }
}

