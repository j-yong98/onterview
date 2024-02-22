package com.quiet.onterview.community.service;

import com.quiet.onterview.community.dto.response.ArticleLikeResponse;

public interface LikesService {

    ArticleLikeResponse likeOrCancelArticle(Long memberId, Long articleId);
}
