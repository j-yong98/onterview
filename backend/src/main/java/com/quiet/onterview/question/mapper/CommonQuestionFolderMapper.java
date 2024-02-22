package com.quiet.onterview.question.mapper;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommonQuestionFolderMapper {

    private final CommonQuestionMapper commonQuestionMapper;

    public CommonQuestionFolderResponse commonQuestionFolderToCommonQuestionFolderResponse(CommonQuestionFolder commonQuestionFolder) {
        List<CommonQuestion> commonQuestionList = commonQuestionFolder.getCommonQuestionList();

        List<CommonQuestionResponse> commonQuestionResponseList = commonQuestionList.stream()
                .map(commonQuestionMapper::commonQuestionToCommonQuestionResponse)
                .collect(Collectors.toList());

        return CommonQuestionFolderResponse.builder()
                .commonQuestionFolderId(commonQuestionFolder.getCommonQuestionFolderId())
                .commonQuestionFolder(commonQuestionFolder.getCommonQuestionFolder())
                .commonQuestionList(commonQuestionResponseList)
                .build();
    }

    public CommonQuestionFolder commonQuestionFolderRequestToEntity(CommonQuestionFolderRequest commonQuestionFolderRequest) {
        return CommonQuestionFolder.builder()
                .commonQuestionFolder(commonQuestionFolderRequest.getCommonQuestionFolder())
                .build();

    }

}