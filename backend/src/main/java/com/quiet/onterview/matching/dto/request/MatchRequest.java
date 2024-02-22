package com.quiet.onterview.matching.dto.request;

import com.quiet.onterview.matching.MatchStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MatchRequest {
    private MatchStatus status;
    private Integer roomId;
    private Integer matchCount;
}
