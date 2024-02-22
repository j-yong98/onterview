package com.quiet.onterview.matching;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MatchUser {
    private String principal;
    private Long memberId;
    private String token;


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MatchUser matchUser = (MatchUser) object;
        return Objects.equals(principal, matchUser.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal);
    }

    public void setToken(String token) {
        this.token = token;
    }
}
