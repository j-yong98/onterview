package com.quiet.onterview.security;

import com.quiet.onterview.member.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SecurityUser {

    private Long memberId;
    private String email;
    private String password;
    private String nickname;
    private String imageUrl;
    private String roles;

    public SecurityUser(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.imageUrl = member.getImageUrl();
        this.roles = member.getRole().toString();
    }
}
