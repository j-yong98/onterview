package com.quiet.onterview.question.mapper;

import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.dto.response.MyQuestionWithFolderResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.video.dto.response.VideoStorageResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyQuestionMapper {

    private final VideoMapper videoMapper;

    public MyQuestionResponse myQuestionToMyQuestionResponse(MyQuestion myQuestion) {
        return MyQuestionResponse.builder()
                .commonQuestionId(Optional.ofNullable(myQuestion.getCommonQuestion())
                        .map(CommonQuestion::getCommonQuestionId)
                        .orElse(null))
                .myQuestionId(myQuestion.getMyQuestionId())
                .question(myQuestion.getQuestion())
                .build();
    }

    public MyQuestionWithFolderResponse myQuestionToMyQuestionByFolderResponse(MyQuestion myQuestion) {
        return MyQuestionWithFolderResponse.builder()
                .myQuestionFolderId(myQuestion.getMyQuestionFolder().getMyQuestionFolderId())
                .myQuestionId(myQuestion.getMyQuestionId())
                .question(myQuestion.getQuestion())
                .build();
    }

    public MyAnswerAndVideoResponse myQuestionToMyAnswerAndVideoResponse(MyQuestion myQuestion) {
        return MyAnswerAndVideoResponse.builder()
                .myQuestionId(myQuestion.getMyQuestionId())
                .question(myQuestion.getQuestion())
                .answer(myQuestion.getAnswer())
                .videoInformationResponseList(videoMapper.allVideoToInformationResponse(myQuestion.getVideoList()))
                .build();
    }

    public MyQuestion myQuestionRequestToEntity(MyQuestionRequest myQuestionRequest) {
        return MyQuestion.builder()
                .question(myQuestionRequest.getQuestion())
                .build();

    }

    public VideoStorageResponse videoToVideoStorageResponse(MyQuestion myQuestion, Video video) {
        return VideoStorageResponse.builder()
                .videoId(video.getVideoId())
                .thumbnailUrl(video.getThumbnailUrl())
                .title(video.getTitle())
                .question(myQuestion.getQuestion())
                .build();
    }
}