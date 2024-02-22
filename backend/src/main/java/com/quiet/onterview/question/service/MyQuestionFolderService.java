package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.video.dto.response.VideoStorageResponse;

import java.util.List;

public interface MyQuestionFolderService {

    List<MyQuestionFolderResponse> getMyQuestionByFolder(Long memberId);
    List<MyQuestionFolderResponse> getMyQuestionByFolderByKeyword(Long memberId, String keyword);
    List<VideoStorageResponse> getSelfVideoList(Long memberId);
    void createMyQuestionFolder(Long memberId, MyQuestionFolderRequest myQuestionFolderRequest);
    void updateMyQuestionFolder(Long memberId, Long myQuestionFolderId, MyQuestionFolderRequest myQuestionFolderRequest);
    void deleteMyQuestionFolder(Long memberId, Long myQuestionFolderId);
}