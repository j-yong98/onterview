package com.quiet.onterview.community.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.community.dto.response.ArticleLikeResponse;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.entity.Likes;
import com.quiet.onterview.community.entity.LikesPrimaryKey;
import com.quiet.onterview.community.repository.ArticleRepository;
import com.quiet.onterview.community.repository.LikesRepository;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ArticleLikeResponse likeOrCancelArticle(Long memberId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        if(article.getMember().getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.REQUEST_CONDITION_NOT_MATCHES);
        }
        makeLikesInformation(memberId,articleId);
        LikesPrimaryKey likesPrimaryKey = getLikesPrimaryKey(memberId, articleId);
        Likes likes = likesRepository.findById(likesPrimaryKey).orElseThrow();
        Boolean newStatus = likes.getStatus().equals(false) ? true : false;
        Integer value = likes.getStatus().equals(false) ? 1 : -1;
        likesRepository.updateStatus(likesPrimaryKey, newStatus);
        articleRepository.updateLikeCount(articleId, value);
        return ArticleLikeResponse.builder()
                .likeCount(likesPrimaryKey.getArticle().getLikeCount()+value)
                .status(newStatus ? "like" : "unlike")
                .build();
    }

    protected LikesPrimaryKey getLikesPrimaryKey(Long memberId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BaseException(ErrorCode.USER_NOT_EXISTS));
        return new LikesPrimaryKey(article, member);
    }

    protected void makeLikesInformation(Long memberId, Long articleId) {
        LikesPrimaryKey likesPrimaryKey = getLikesPrimaryKey(memberId, articleId);
        Optional<Likes> likes = likesRepository.findById(likesPrimaryKey);
        if(!likes.isPresent()) {
            Likes newLikes = Likes.builder()
                    .likesPrimaryKey(likesPrimaryKey)
                    .status(false)
                    .build();
            likesRepository.save(newLikes);
        }
    }

    public Boolean getMyLikeStatus(Long memberId, Long articleId) {
        LikesPrimaryKey likesPrimaryKey = getLikesPrimaryKey(memberId, articleId);
        Optional<Likes> likes = likesRepository.findById(likesPrimaryKey);
        return likes.isPresent() ? likes.get().getStatus() : false;
    }
}
