package com.quiet.onterview.community.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleLikeResponse {

    Integer likeCount;
    String status;
}
