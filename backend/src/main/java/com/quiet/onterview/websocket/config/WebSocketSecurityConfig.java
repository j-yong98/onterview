package com.quiet.onterview.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
public class WebSocketSecurityConfig {

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
        return MessageMatcherDelegatingAuthorizationManager.builder()
                .nullDestMatcher().permitAll()
                .simpMessageDestMatchers("/pub", "/server").authenticated()
                .simpSubscribeDestMatchers("/sub", "/client").authenticated()
                .anyMessage().denyAll().build();
    }
}
