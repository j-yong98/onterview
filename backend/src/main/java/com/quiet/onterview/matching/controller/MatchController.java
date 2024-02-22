package com.quiet.onterview.matching.controller;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.service.MatchService;
import com.quiet.onterview.websocket.StompUser;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @MessageMapping("/enter")
    public void enter(@Payload MatchRequest matchRequest, Principal user) {
        StompUser stompUser = (StompUser) user;
        log.info("ENTER MATCH : {}", matchRequest.toString());
        matchService.enter(matchRequest, stompUser.getName(), stompUser.getMemberId());
    }
}
