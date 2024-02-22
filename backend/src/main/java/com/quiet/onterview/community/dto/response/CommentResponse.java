package com.quiet.onterview.community.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    Long parentCommentId;
    Long commentId;
    String writerNickname;
    String content;
    String writtenDate;
    Boolean isMyComment;
    Boolean isWriterComment;
}
