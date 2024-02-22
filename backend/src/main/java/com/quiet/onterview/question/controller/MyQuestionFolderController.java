package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.service.MyQuestionFolderService;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.video.dto.response.VideoStorageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "my-question-folder-controller", description = "나의 면접 문항 폴더 컨트롤러")
@RestController
@RequestMapping("/api/my-question-folder")
@RequiredArgsConstructor
public class MyQuestionFolderController {
    private final MyQuestionFolderService myQuestionFolderService;

    @Operation(summary = "GET 방식으로 나의 면접 문항 폴더별 질문 리스트 전체 조회 & 키워드 조회")
    @GetMapping
    public ResponseEntity<List<MyQuestionFolderResponse>> getMyQuestionList(
            @AuthenticationPrincipal SecurityUser user,
            @RequestParam(name = "keyword", required = false) String keyword) {
        if (keyword == null) {
            return ResponseEntity.ok(myQuestionFolderService.getMyQuestionByFolder(user.getMemberId()));
        } else {
            return ResponseEntity.ok(myQuestionFolderService.getMyQuestionByFolderByKeyword(user.getMemberId(), keyword));
        }
    }

    @Operation(summary = "GET 방식으로 셀프 스피치 영상 전체 조회")
    @GetMapping("/video")
    public ResponseEntity<List<VideoStorageResponse>> getSelfVideoList(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(myQuestionFolderService.getSelfVideoList(user.getMemberId()));
    }

    @Operation(summary = "POST 방식으로 나의 면접 질문 폴더 생성")
    @PostMapping
    public ResponseEntity<Void> registerMyQuestionFolder(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody MyQuestionFolderRequest myQuestionFolderRequest) {
        myQuestionFolderService.createMyQuestionFolder(user.getMemberId(), myQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 나의 면접 질문 폴더명 수정")
    @PatchMapping("/{my_question_folder_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("my_question_folder_id") Long myQuestionFolderId,
            @RequestBody MyQuestionFolderRequest myQuestionFolderRequest
    ) {
        myQuestionFolderService.updateMyQuestionFolder(user.getMemberId(), myQuestionFolderId, myQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 나의 면접 질문 폴더 삭제")
    @DeleteMapping("/{my_question_folder_id}")
    public ResponseEntity<Void> deleteMyQuestionFolder(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("my_question_folder_id") Long myQuestionFolderId) {
        myQuestionFolderService.deleteMyQuestionFolder(user.getMemberId(), myQuestionFolderId);
        return ResponseEntity.ok().build();
    }

}
