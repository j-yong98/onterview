package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.MyAnswerUpdateRequest;
import com.quiet.onterview.question.dto.request.MyQuestionMoveRequest;
import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.request.MyQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.dto.response.MyQuestionWithFolderResponse;

import java.util.List;

public interface MyQuestionService {

    List<MyQuestionWithFolderResponse> getAllMyQuestion(Long memberId);
    List<MyQuestionWithFolderResponse> getMyQuestionByKeyword(Long memberId, String keyword);
    MyAnswerAndVideoResponse getMyAnswerAndVideo(Long memberId, Long myQuestionId);
    void createMyQuestion(Long memberId, MyQuestionRequest myQuestionRequest);
    void updateMyQuestion(Long memberId, Long myQuestionId, MyQuestionUpdateRequest myQuestionUpdateRequest);
    void updateMyAnswer(Long memberId, Long myQuestionId, MyAnswerUpdateRequest myAnswerUpdateRequest);
    void deleteMyQuestion(Long memberId, Long myQuestionId);
    void moveMyQuestion(Long memberId, MyQuestionMoveRequest myQuestionMoveRequest);
}