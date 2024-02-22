package com.quiet.onterview.websocket;

import java.security.Principal;
import lombok.Setter;

public class StompUser implements Principal {
    private String name;
    @Setter
    private Long memberId;

    public StompUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Long getMemberId() {
        return memberId;
    }
}
