package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.CommonQuestionRequest;
import com.quiet.onterview.question.dto.request.CommonQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionWithFolderResponse;
import com.quiet.onterview.question.service.CommonQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "common-question-controller", description = "빈출 면접 문항 컨트롤러")
@RestController
@RequestMapping("/api/common-question")
@RequiredArgsConstructor
public class CommonQuestionController {

    private final CommonQuestionService commonQuestionService;

    @Operation(summary = "GET 방식으로 빈출 면접 질문 전체 조회")
    @GetMapping
    public ResponseEntity<List<CommonQuestionWithFolderResponse>> getCommonQuestionList(
            @RequestParam(name = "keyword", required = false) String keyword) {
        if (keyword == null) {
            return ResponseEntity.ok(commonQuestionService.getAllCommonQuestion());
        } else {
            return ResponseEntity.ok(commonQuestionService.getCommonQuestionByKeyword(keyword));
        }
    }

    @Operation(summary = "POST 방식으로 빈출 면접 질문 생성")
    @PostMapping
    public ResponseEntity<Void> registerCommonQuestion(@RequestBody CommonQuestionRequest commonQuestionRequest) {
        commonQuestionService.createCommonQuestion(commonQuestionRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 빈출 면접 질문 수정")
    @PatchMapping("/{common_question_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @PathVariable("common_question_id") Long commonQuestionId,
            @RequestBody CommonQuestionUpdateRequest commonQuestionUpdateRequest
    ) {
        commonQuestionService.updateCommonQuestion(commonQuestionId, commonQuestionUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 빈출 면접 문항 삭제")
    @DeleteMapping("/{common_question_id}")
    public ResponseEntity<Void> deleteMyQuestion(@PathVariable("common_question_id") Long commonQuestionId) {
        commonQuestionService.deleteCommonQuestion(commonQuestionId);
        return ResponseEntity.ok().build();
    }
}
