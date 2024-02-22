package com.quiet.onterview.community.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentObjectResponse {
    CommentResponse parentComment;
    List<CommentResponse> childCommentList;
}
