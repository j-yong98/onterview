package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionRequest;
import com.quiet.onterview.question.dto.request.CommonQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionWithFolderResponse;

import java.util.List;

public interface CommonQuestionService {
    List<CommonQuestionWithFolderResponse> getAllCommonQuestion();
    List<CommonQuestionWithFolderResponse> getCommonQuestionByKeyword(String keyword);
    void createCommonQuestion(CommonQuestionRequest commonQuestionRequest);
    void updateCommonQuestion(Long commonQuestionId, CommonQuestionUpdateRequest commonQuestionUpdateRequest);
    void deleteCommonQuestion(Long commonQuestionId);
}