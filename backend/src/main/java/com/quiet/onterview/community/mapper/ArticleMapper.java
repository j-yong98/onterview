package com.quiet.onterview.community.mapper;

import static com.quiet.onterview.utils.DateFormatter.communityDateFormatter;

import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.response.*;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.utils.DateFormatter;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

    private final VideoMapper videoMapper;

    public ArticleListResponse articleToArticleListResponse(Article article) {
        return ArticleListResponse.builder()
                .articleId(article.getArticleId())
                .writerNickname(article.getMember().getNickname())
                .title(article.getTitle())
                .likeCount(article.getLikeCount())
                .commentCount(article.getCommentCount())
                .writtenDate(article.getCreateAt().format(communityDateFormatter))
                .build();
    }

    public Article articlePostRequestToArticle(Member member, Video video,
            ArticlePostRequest articlePostRequest) {
        return Article.builder()
                .member(member)
                .video(video)
                .title(articlePostRequest.getTitle())
                .content(articlePostRequest.getContent())
                .likeCount(0)
                .commentCount(0)
                .build();
    }

    public ArticlePostResponse articleToArticlePostResponse(Article article) {
        return ArticlePostResponse.builder()
                .articleId(article.getArticleId())
                .videoId(article.getVideo().getVideoId())
                .title(article.getTitle())
                .content(article.getContent())
                .likeCount(article.getLikeCount())
                .commentCount(article.getCommentCount())
                .writtenDate(article.getCreateAt().format(communityDateFormatter))
                .build();
    }

    public ArticleInfoResponse articleToArticleInfoResponse(Article article, Long memberId,
            Boolean isLikedByMe) {
        return ArticleInfoResponse.builder()
                .title(article.getTitle())
                .writerNickname(article.getMember().getNickname())
                .content(article.getContent())
                .videoInfo(videoMapper.videoToDetailResponse(article.getVideo()))
                .isMyArticle(article.getMember().getMemberId().equals(memberId))
                .likeCount(article.getLikeCount())
                .isLiked(isLikedByMe)
                .writtenDate(article.getCreateAt().format(DateFormatter.communityDateFormatter))
                .build();
    }
}
