package com.quiet.onterview.community.dto.response;

import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleInfoResponse {

    String title;
    String writerNickname;
    String content;
    VideoDetailResponse videoInfo;
    Boolean isMyArticle;
    Integer likeCount;
    Boolean isLiked;
    String writtenDate;
}
