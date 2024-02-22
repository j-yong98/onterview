package com.quiet.onterview.community.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.community.dto.request.ArticleModifyContentRequest;
import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.response.ArticleInfoResponse;
import com.quiet.onterview.community.dto.response.ArticleListResponse;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;
import com.quiet.onterview.community.dto.response.ArticleResponse;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.mapper.ArticleMapper;
import com.quiet.onterview.community.repository.ArticleRepository;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.exception.VideoNotFoundException;
import com.quiet.onterview.video.repository.VideoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;
    private final LikesServiceImpl likesService;
    private final CommentService commentService;

    @Override
    public ArticlePostResponse postArticle(Long memberId, ArticlePostRequest articlePostRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BaseException(ErrorCode.USER_NOT_EXISTS));
        Video video = videoRepository.findById(articlePostRequest.getVideoId()).orElseThrow(
                VideoNotFoundException::new);

        switch (articlePostRequest.getRoomType()) {
            case SELF -> {
                if (!video.getMyQuestion().getMyQuestionFolder().getMember().getMemberId()
                        .equals(memberId)) {
                    throw new BaseException(ErrorCode.REQUEST_CONDITION_NOT_MATCHES);
                }
            }

            case SINGLE, MULTI -> {
                if (!video.getInterviewQuestion().getInterviewee().getMember().getMemberId()
                        .equals(memberId)) {
                    throw new BaseException(ErrorCode.REQUEST_CONDITION_NOT_MATCHES);
                }
            }
        }

        Article article = articleMapper.articlePostRequestToArticle(member, video,
                articlePostRequest);
        return articleMapper.articleToArticlePostResponse(articleRepository.save(article));
    }

    @Override
    public void modifyArticleContent(Long memberId, Long articleId,
            ArticleModifyContentRequest articleModifyContentRequest) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        if(!article.getMember().getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.ARTICLE_WRITER_NOT_MATCHES);
        }
        if(articleModifyContentRequest.getContent().isEmpty()) {
            throw new BaseException(ErrorCode.REQUIRED_VALUE_NOT_EXISTS);
        }
        articleRepository.updateContent(articleId,articleModifyContentRequest.getContent());
    }

    @Override
    public void deleteArticle(Long memberId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        if(!article.getMember().getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.ARTICLE_WRITER_NOT_MATCHES);
        }
        articleRepository.deleteById(articleId);
    }

    @Override
    public List<ArticleListResponse> getAllMyArticle(Long memberId, String order, String category, String query) {
        List<ArticleListResponse> articleListResponse = new ArrayList<>();
        List<Article> articleList = articleRepository.searchByCategorySortByOrder(memberId, order, category, query);
        articleList.stream().forEach(article ->
                articleListResponse.add(articleMapper.articleToArticleListResponse(article)));
        return articleListResponse;
    }

    @Override
    public ArticleInfoResponse getArticleInformation(Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        Boolean likesStatus = likesService.getMyLikeStatus(memberId, articleId);
        return articleMapper.articleToArticleInfoResponse(article,memberId, likesStatus);
    }

    @Override
    public ArticleResponse getArticleDetail(Long articleId, Long memberId) {
        return ArticleResponse.builder()
                .articleInfo(getArticleInformation(articleId, memberId))
                .commentInfo(commentService.getArticleCommentInfo(articleId, memberId))
                .build();
    }

    @Override
    public List<ArticleListResponse> getAllArticle(String order, String category, String query) {
        List<ArticleListResponse> articleListResponse = new ArrayList<>();
        List<Article> articleList = articleRepository.searchByCategorySortByOrder(null, order, category, query);
        articleList.stream().forEach(article ->
                articleListResponse.add(articleMapper.articleToArticleListResponse(article)));
        return articleListResponse;
    }
}
