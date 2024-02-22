package com.quiet.onterview.community.dto.request;

import com.quiet.onterview.interview.entity.RoomType;
import lombok.Getter;

@Getter
public class ArticlePostRequest {

    Long videoId;
    String title;
    String content;
    RoomType roomType;
}