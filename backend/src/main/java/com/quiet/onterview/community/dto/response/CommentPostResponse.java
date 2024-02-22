package com.quiet.onterview.community.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentPostResponse {

    Long articleId;
    Long parentCommentId;
    Long commentId;
    String content;
    String nickname;
    String writtenDate;
    Boolean isMyComment;
    Boolean isWriterComment;
}