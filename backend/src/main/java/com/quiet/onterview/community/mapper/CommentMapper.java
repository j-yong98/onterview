package com.quiet.onterview.community.mapper;

import com.quiet.onterview.community.dto.request.CommentPostRequest;
import com.quiet.onterview.community.dto.response.CommentPostResponse;
import com.quiet.onterview.community.dto.response.CommentResponse;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.entity.Comment;
import com.quiet.onterview.member.entity.Member;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public Comment commentPostRequestToComment(CommentPostRequest commentPostRequest, Comment parent, Article article, Member member) {
        return new Comment(null, article,
                member, commentPostRequest.getContent(),
                parent, new ArrayList<>());
    }

    public CommentPostResponse commentToCommentPostResponse(Comment comment, Long memberId) {
        return CommentPostResponse.builder()
                .articleId(comment.getArticle().getArticleId())
                .parentCommentId(comment.getParent()==null ? 0 : comment.getParent().getCommentId())
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .nickname(comment.getMember().getNickname())
                .writtenDate(comment.getCreateAt().format(formatter))
                .isMyComment((memberId == comment.getMember().getMemberId()))
                .isWriterComment(comment.getArticle().getMember().getMemberId() == comment.getMember().getMemberId())
                .build();
    }

    public CommentResponse commentToCommentResponse(Comment comment, Long memberId) {
        return CommentResponse.builder()
                .parentCommentId(comment.getParent() == null ? null : comment.getParent().getCommentId())
                .commentId(comment.getCommentId())
                .writerNickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .writtenDate(comment.getCreateAt().format(formatter))
                .isMyComment((comment.getMember().getMemberId()).equals(memberId))
                .isWriterComment(comment.getArticle().getMember().getMemberId()==comment.getMember().getMemberId())
                .build();
    }
}
