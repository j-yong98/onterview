package com.quiet.onterview.community.controller;

import com.quiet.onterview.community.dto.request.ArticleModifyContentRequest;
import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.request.CommentPostRequest;
import com.quiet.onterview.community.dto.response.ArticleInfoResponse;
import com.quiet.onterview.community.dto.response.ArticleLikeResponse;
import com.quiet.onterview.community.dto.response.ArticleListResponse;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;
import com.quiet.onterview.community.dto.response.ArticleResponse;
import com.quiet.onterview.community.dto.response.CommentListResponse;
import com.quiet.onterview.community.dto.response.CommentPostResponse;
import com.quiet.onterview.community.service.ArticleService;
import com.quiet.onterview.community.service.CommentService;
import com.quiet.onterview.community.service.LikesService;
import com.quiet.onterview.security.SecurityUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final LikesService likesService;

    @GetMapping
    public ResponseEntity<List<ArticleListResponse>> getAllArticle(@RequestParam("order") String order,
            @RequestParam("category") String category,
            @RequestParam("query") String query) {
        return ResponseEntity.ok().body(articleService.getAllArticle(order, category, query));
    }

    @PostMapping
    public ResponseEntity<ArticlePostResponse> postArticle(@AuthenticationPrincipal SecurityUser user,
            @RequestBody ArticlePostRequest articlePostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.postArticle(user.getMemberId(),articlePostRequest));
    }

    @PatchMapping("/{articleId}")
    public ResponseEntity modifyArticleContent(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId,
            @RequestBody ArticleModifyContentRequest articleModifyContentRequest) {
        articleService.modifyArticleContent(user.getMemberId(), articleId, articleModifyContentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId) {
        articleService.deleteArticle(user.getMemberId(), articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detail/{articleId}")
    public ResponseEntity<ArticleResponse> getArticleDetail(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(articleService.getArticleDetail(articleId, user.getMemberId()));
    }

    @GetMapping("/post/detail/{articleId}")
    public ResponseEntity<ArticleInfoResponse> getArticleInformation(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(articleService.getArticleInformation(articleId, user.getMemberId()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ArticleListResponse>> getAllMyArticle(@AuthenticationPrincipal SecurityUser user,
            @RequestParam("order") String order,
            @RequestParam("category") String category,
            @RequestParam("query") String query) {
        return ResponseEntity.ok().body(articleService.getAllMyArticle(user.getMemberId(), order, category, query));
    }

    @PatchMapping("/like/{articleId}")
    public ResponseEntity<ArticleLikeResponse> likeOrCancelArticle(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(likesService.likeOrCancelArticle(user.getMemberId(), articleId));
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentPostResponse> postComment(@AuthenticationPrincipal SecurityUser user,
            @RequestBody CommentPostRequest commentPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.postComment(user.getMemberId(), commentPostRequest));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteComment(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(user.getMemberId(), commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comment/detail/{articleId}")
    public ResponseEntity<CommentListResponse> getArticleCommentList(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(commentService.getArticleCommentInfo(articleId, user.getMemberId()));
    }
}
