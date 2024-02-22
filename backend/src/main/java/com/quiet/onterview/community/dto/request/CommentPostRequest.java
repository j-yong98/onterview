package com.quiet.onterview.community.dto.request;

import lombok.Getter;

@Getter
public class CommentPostRequest {

    Long articleId;
    Long parentCommentId;
    String content;
}
