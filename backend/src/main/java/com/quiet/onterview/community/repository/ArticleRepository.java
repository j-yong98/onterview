package com.quiet.onterview.community.repository;

import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.repository.querydsl.ArticleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleQueryRepository {

    @Modifying
    @Query("update Article a set a.content = :content where a.articleId = :articleId")
    int updateContent(Long articleId, String content);

    @Modifying
    @Query("update Article a set a.likeCount = a.likeCount + :value where a.articleId = :articleId")
    int updateLikeCount(Long articleId, Integer value);

    @Modifying
    @Query("update Article a set a.commentCount = a.commentCount + :value where a.articleId = :articleId")
    void updateCommentCount(Long articleId, Integer value);
}
