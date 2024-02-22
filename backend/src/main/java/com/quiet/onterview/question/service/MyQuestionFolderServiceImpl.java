package com.quiet.onterview.question.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.exception.MyQuestionFolderNotFoundException;
import com.quiet.onterview.question.mapper.MyQuestionFolderMapper;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import com.quiet.onterview.video.dto.response.VideoStorageResponse;
import com.quiet.onterview.video.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyQuestionFolderServiceImpl implements MyQuestionFolderService {

    private final MyQuestionFolderRepository myQuestionFolderRepository;
    private final MyQuestionFolderMapper myQuestionFolderMapper;
    private final MyQuestionMapper myQuestionMapper;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MyQuestionFolderResponse> getMyQuestionByFolder(Long memberId) {
        List<MyQuestionFolder> myQuestionFolderList = myQuestionFolderRepository.findMyQuestionByFolder(memberId);
        return myQuestionFolderList.stream()
                .map(myQuestionFolderMapper::myQuestionFolderToMyQuestionFolderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyQuestionFolderResponse> getMyQuestionByFolderByKeyword(Long memberId, String keyword) {
        List<MyQuestionFolderResponse> result = new ArrayList<>();
        List<Long> myQuestionFolderIdList = myQuestionFolderRepository.findMyQuestionFolderIdList(memberId);

        for (Long folderId : myQuestionFolderIdList) {
            List<MyQuestion> myQuestionList = myQuestionFolderRepository.findMyQuestionByFolderByKeyword(memberId, folderId, keyword);

            if (!myQuestionList.isEmpty()) {
                List<MyQuestionResponse> myQuestionResponsesList = myQuestionList.stream()
                        .map(myQuestionMapper::myQuestionToMyQuestionResponse)
                        .toList();
                result.add(MyQuestionFolderResponse.builder()
                        .myQuestionFolderId(folderId)
                        .myQuestionFolder(myQuestionList.get(0).getMyQuestionFolder().getMyQuestionFolder())
                        .myQuestionList(myQuestionResponsesList)
                        .build());
            }
        }
        return result;
    }

    @Override
    public List<VideoStorageResponse> getSelfVideoList(Long memberId) {
        List<VideoStorageResponse> result = new ArrayList<>();
        List<MyQuestionFolder> myQuestionFolderList = myQuestionFolderRepository.findMyQuestionByFolder(memberId);
        for (MyQuestionFolder myQuestionFolder : myQuestionFolderList) {
            List<MyQuestion> myQuestionList = myQuestionFolder.getMyQuestionList();
            for (MyQuestion myQuestion : myQuestionList) {
                List<Video> videoList = myQuestion.getVideoList();
                if (!videoList.isEmpty()) {
                    for (Video video : videoList) {
                        VideoStorageResponse videoStorageResponse = myQuestionMapper.videoToVideoStorageResponse(myQuestion, video);
                        result.add(videoStorageResponse);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void createMyQuestionFolder(Long memberId, MyQuestionFolderRequest myQuestionFolderRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(ErrorCode.MEMBERID_NOT_EXISTS));
        MyQuestionFolder myQuestionFolder = myQuestionFolderMapper.myQuestionFolderRequestToEntity(member, myQuestionFolderRequest);
        myQuestionFolderRepository.save(myQuestionFolder);
    }

    @Override
    public void updateMyQuestionFolder(Long memberId, Long myQuestionFolderId, MyQuestionFolderRequest myQuestionFolderRequest) {
        MyQuestionFolder myQuestionFolder = myQuestionFolderRepository.findById(myQuestionFolderId)
                .filter(folder -> folder.getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionFolderNotFoundException::new);

        Optional.ofNullable(myQuestionFolderRequest.getMyQuestionFolder())
                .ifPresent(myQuestionFolder::updateMyQuestionFolder);
    }

    @Override
    public void deleteMyQuestionFolder(Long memberId, Long myQuestionFolderId) {
        myQuestionFolderRepository.findById(myQuestionFolderId)
                .filter(folder -> folder.getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionFolderNotFoundException::new);
        myQuestionFolderRepository.deleteById(myQuestionFolderId);
    }

}