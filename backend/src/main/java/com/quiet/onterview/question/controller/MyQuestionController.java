package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.MyAnswerUpdateRequest;
import com.quiet.onterview.question.dto.request.MyQuestionMoveRequest;
import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.request.MyQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.dto.response.MyQuestionWithFolderResponse;
import com.quiet.onterview.question.service.MyQuestionService;
import com.quiet.onterview.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "my-question-controller", description = "나의 면접 문항 컨트롤러")
@RestController
@RequestMapping("/api/my-question")
@RequiredArgsConstructor
public class MyQuestionController {

    private final MyQuestionService myQuestionService;

    @Operation(summary = "GET 방식으로 나의 면접 질문 전체 조회 & 키워드 조회")
    @GetMapping
    public ResponseEntity<List<MyQuestionWithFolderResponse>> getMyQuestionList(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam(name = "keyword", required = false) String keyword) {
        if (keyword == null) {
            return ResponseEntity.ok(myQuestionService.getAllMyQuestion(user.getMemberId()));
        } else {
            return ResponseEntity.ok(myQuestionService.getMyQuestionByKeyword(user.getMemberId(), keyword));
        }
    }

    @Operation(summary = "GET 방식으로 특정 면접 문항에 대한 답변과 영상 조회")
    @GetMapping("/{my_question_id}")
    public ResponseEntity<MyAnswerAndVideoResponse> getMyAnswerAndVideo(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("my_question_id") Long myQuestionId) {
        return ResponseEntity.ok(myQuestionService.getMyAnswerAndVideo(user.getMemberId(), myQuestionId));
    }

    @Operation(summary = "POST 방식으로 나의 면접 질문 생성")
    @PostMapping
    public ResponseEntity<Void> registerMyQuestion(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody MyQuestionRequest myQuestionRequest) {
        myQuestionService.createMyQuestion(user.getMemberId(), myQuestionRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 나의 면접 질문 수정")
    @PatchMapping("/{my_question_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("my_question_id") Long myQuestionId,
            @RequestBody MyQuestionUpdateRequest myQuestionUpdateRequest
    ) {
        myQuestionService.updateMyQuestion(user.getMemberId(), myQuestionId, myQuestionUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 특정 면접 문항에 대한 답변 수정")
    @PatchMapping("/answer/{my_question_id}")
    public ResponseEntity<Void> updateMyAnswer(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("my_question_id") Long myQuestionId,
            @RequestBody MyAnswerUpdateRequest myAnswerUpdateRequest
    ) {
        myQuestionService.updateMyAnswer(user.getMemberId(), myQuestionId, myAnswerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 나의 면접 문항 삭제")
    @DeleteMapping("/{my_question_id}")
    public ResponseEntity<Void> deleteMyQuestion(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("my_question_id") Long myQuestionId) {
        myQuestionService.deleteMyQuestion(user.getMemberId(), myQuestionId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 나의 면접 질문 폴더간 이동")
    @PatchMapping("/move")
    public ResponseEntity<Void> moveMyQuestion(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody MyQuestionMoveRequest myQuestionMoveRequest) {
        myQuestionService.moveMyQuestion(user.getMemberId(), myQuestionMoveRequest);
        return ResponseEntity.ok().build();
    }
}
