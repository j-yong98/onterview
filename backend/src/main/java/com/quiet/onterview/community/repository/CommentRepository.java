package com.quiet.onterview.community.repository;

import com.quiet.onterview.community.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // TODO : inner join으로 바꿔보기 (동작하는거 찾아보기)
    @Query("select c from Comment c where c.article.articleId = :articleId and c.parent is null")
    List<Comment> findAllParentCommentByArticleId(Long articleId);

    @Query("select c from Comment c where c.parent.commentId = :parentId")
    List<Comment> findAllChildCommentByCommentId(Long parentId);

    Long countByArticle_ArticleId(Long articleId);

    Long countByArticle_ArticleIdAndCommentId(Long articleId, Long commentId);
}
