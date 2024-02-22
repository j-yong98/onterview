package com.quiet.onterview.member.dto.request;

import lombok.Getter;

@Getter
public class MemberSignupRequest {

    String email;
    String password;
    String confirm;
    String nickname;
}
