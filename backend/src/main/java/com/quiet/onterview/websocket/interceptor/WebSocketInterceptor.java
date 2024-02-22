package com.quiet.onterview.websocket.interceptor;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.SEND;
import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;

import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.exception.SecurityException;
import com.quiet.onterview.security.jwt.JwtTokenProvider;
import com.quiet.onterview.websocket.StompUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        StompCommand command = accessor.getCommand();
        String token = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
        if (command == CONNECT || command == SUBSCRIBE || command == SEND) {
            if (!jwtTokenProvider.isValidToken(token)) {
                throw new SecurityException(ErrorCode.ACCESS_TOKEN_EXPIRED);
            }
            setPrincipal(token, accessor);
        }
        return message;
    }

    private void setPrincipal(String token, StompHeaderAccessor accessor) {
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new SecurityException(ErrorCode.EMAIL_NOT_EXISTS));
        StompUser user = (StompUser) accessor.getUser();
        user.setMemberId(member.getMemberId());
    }
}
