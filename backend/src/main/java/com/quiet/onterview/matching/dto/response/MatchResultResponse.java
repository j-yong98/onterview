package com.quiet.onterview.matching.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchResultResponse {
    Long roomId;
    String sessionId;
    String token;
    Integer index;
}
