package com.quiet.onterview.member.dto.request;

import lombok.Getter;

@Getter
public class MemberModifyPasswordRequest {

    String original;
    String password;
    String confirm;
}
