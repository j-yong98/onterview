package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionFolderResponse;
import com.quiet.onterview.question.entity.CommonQuestion;

import java.util.List;

public interface CommonQuestionFolderService {

    List<CommonQuestionFolderResponse> getCommonQuestionByFolder();
    List<CommonQuestionFolderResponse> getCommonQuestionByFolderByKeyword(String keyword);
    CommonQuestionFolderResponse getOneCommonQuestionFolderInfo(Long commonQuestionFolderId);
    void createCommonQuestionFolder(CommonQuestionFolderRequest commonQuestionFolderRequest);
    void updateCommonQuestionFolder(Long commonQuestionFolderId, CommonQuestionFolderRequest commonQuestionFolderRequest);
    void deleteCommonQuestionFolder(Long commonQuestionFolderId);
    List<CommonQuestion> getRandomCommonQuestionList(String commonQuestionFolderName, int numToSelect);
}