package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import com.quiet.onterview.question.exception.*;
import com.quiet.onterview.question.mapper.CommonQuestionFolderMapper;
import com.quiet.onterview.question.mapper.CommonQuestionMapper;
import com.quiet.onterview.question.repository.CommonQuestionFolderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonQuestionFolderServiceImpl implements CommonQuestionFolderService {

    private final CommonQuestionFolderRepository commonQuestionFolderRepository;
    private final CommonQuestionFolderMapper commonQuestionFolderMapper;
    private final CommonQuestionMapper commonQuestionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommonQuestionFolderResponse> getCommonQuestionByFolder() {
        List<CommonQuestionFolder> commonQuestionFolderList = commonQuestionFolderRepository.findCommonQuestionByFolder();
        return commonQuestionFolderList.stream()
                .map(commonQuestionFolderMapper::commonQuestionFolderToCommonQuestionFolderResponse)
                .toList();
    }

    @Override
    public List<CommonQuestionFolderResponse> getCommonQuestionByFolderByKeyword(String keyword) {
        List<CommonQuestionFolderResponse> result = new ArrayList<>();
        List<Long> commonQuestionFolderIdList = commonQuestionFolderRepository.findCommonQuestionFolderIdList();

        for (Long folderId : commonQuestionFolderIdList) {
            List<CommonQuestion> commonQuestionList = commonQuestionFolderRepository.findCommonQuestionByFolderByKeyword(folderId, keyword);

            if (!commonQuestionList.isEmpty()) {
                List<CommonQuestionResponse> commonQuestionResponsesList = commonQuestionList.stream()
                        .map(commonQuestionMapper::commonQuestionToCommonQuestionResponse)
                        .toList();
                result.add(CommonQuestionFolderResponse.builder()
                        .commonQuestionFolderId(folderId)
                        .commonQuestionFolder(commonQuestionList.get(0).getCommonQuestionFolder().getCommonQuestionFolder())
                        .commonQuestionList(commonQuestionResponsesList)
                        .build());
            }
        }
        return result;
    }

    @Override
    public CommonQuestionFolderResponse getOneCommonQuestionFolderInfo(
            Long commonQuestionFolderId
    ) {
        CommonQuestionFolder commonQuestionFolder = commonQuestionFolderRepository.findOneCommonQuestionFolderInfo(
                        commonQuestionFolderId)
                .orElseThrow(CommonQuestionFolderNotFoundException::new);

        return commonQuestionFolderMapper.commonQuestionFolderToCommonQuestionFolderResponse(
                commonQuestionFolder);
    }


    @Override
    public void createCommonQuestionFolder(
            CommonQuestionFolderRequest commonQuestionFolderRequest
    ) {
        CommonQuestionFolder commonQuestionFolder = commonQuestionFolderMapper.commonQuestionFolderRequestToEntity(
                commonQuestionFolderRequest);
        commonQuestionFolderRepository.save(commonQuestionFolder);
    }

    @Override
    public void updateCommonQuestionFolder(
            Long commonQuestionFolderId,
            CommonQuestionFolderRequest commonQuestionFolderRequest
    ) {
        CommonQuestionFolder commonQuestionFolder = commonQuestionFolderRepository.findById(
                        commonQuestionFolderId)
                .orElseThrow(CommonQuestionFolderNotFoundException::new);
        Optional.ofNullable(commonQuestionFolderRequest.getCommonQuestionFolder())
                .ifPresent(commonQuestionFolder::updateCommonQuestionFolder);
    }

    @Override
    public void deleteCommonQuestionFolder(Long commonQuestionFolderId) {
        commonQuestionFolderRepository.delete(
                commonQuestionFolderRepository.findById(commonQuestionFolderId)
                        .orElseThrow(CommonQuestionFolderNotFoundException::new));
    }

    @Override
    public List<CommonQuestion> getRandomCommonQuestionList(
            String commonQuestionFolderName,
            int numToSelect
    ) {
        CommonQuestionFolder commonQuestionFolder =
                commonQuestionFolderRepository.findInterviewQuestionFolder(commonQuestionFolderName)
                        .orElseThrow(CommonQuestionFolderNotFoundException::new);

        List<CommonQuestion> allQuestions = commonQuestionFolder.getCommonQuestionList();

        if (allQuestions.isEmpty()) {
            throw new QuestionFolderEmptyException();
        }
        if (numToSelect > allQuestions.size()) {
            throw new InvalidSelectionCountException();
        }

        return Stream.concat(
                Stream.of(allQuestions.get(0)),
                Stream.concat(
                        allQuestions.stream()
                                .skip(2).toList()
                                .stream()
                                .limit(numToSelect - 2),
                        Stream.of(allQuestions.get(1))
                )
        ).collect(Collectors.toList());
    }

}