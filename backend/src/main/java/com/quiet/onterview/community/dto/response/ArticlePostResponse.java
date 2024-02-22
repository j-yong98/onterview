package com.quiet.onterview.community.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticlePostResponse {

    Long articleId;
    Long videoId;
    String title;
    String content;
    Integer likeCount;
    Integer commentCount;
    String writtenDate;
}