package com.quiet.onterview.question.mapper;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyQuestionFolderMapper {

    private final MyQuestionMapper myQuestionMapper;

    public MyQuestionFolderResponse myQuestionFolderToMyQuestionFolderResponse(MyQuestionFolder myQuestionFolder) {
        List<MyQuestion> myQuestionList = myQuestionFolder.getMyQuestionList();

        List<MyQuestionResponse> myQuestionResponsesList = myQuestionList.stream()
                .map(myQuestionMapper::myQuestionToMyQuestionResponse)
                .collect(Collectors.toList());

        return MyQuestionFolderResponse.builder()
                .myQuestionFolderId(myQuestionFolder.getMyQuestionFolderId())
                .myQuestionFolder(myQuestionFolder.getMyQuestionFolder())
                .myQuestionList(myQuestionResponsesList)
                .build();
    }

    public MyQuestionFolder myQuestionFolderRequestToEntity(Member member, MyQuestionFolderRequest myQuestionFolderRequest) {
        return MyQuestionFolder.builder()
                .member(member)
                .myQuestionFolder(myQuestionFolderRequest.getMyQuestionFolder())
                .build();

    }

}