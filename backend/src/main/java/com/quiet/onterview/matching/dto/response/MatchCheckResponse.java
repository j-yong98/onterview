package com.quiet.onterview.matching.dto.response;

import com.quiet.onterview.matching.MatchStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchCheckResponse {

    MatchStatus status;
}
