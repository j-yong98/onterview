package com.quiet.onterview.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArticleListResponse {

    Long articleId;
    String writerNickname;
    String title;
    Integer likeCount;
    Integer commentCount;
    String writtenDate;
}
