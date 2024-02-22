package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.MyAnswerUpdateRequest;
import com.quiet.onterview.question.dto.request.MyQuestionMoveRequest;
import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.request.MyQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.dto.response.MyQuestionWithFolderResponse;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.exception.CommonQuestionNotFoundException;
import com.quiet.onterview.question.exception.MyQuestionFolderNotFoundException;
import com.quiet.onterview.question.exception.MyQuestionFolderNotMatchException;
import com.quiet.onterview.question.exception.MyQuestionNotFoundException;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.question.repository.CommonQuestionRepository;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyQuestionServiceImpl implements MyQuestionService{

    private final MyQuestionFolderRepository myQuestionFolderRepository;
    private final MyQuestionRepository myQuestionRepository;
    private final CommonQuestionRepository commonQuestionRepository;
    private final MyQuestionMapper myQuestionMapper;

    @Override
    public List<MyQuestionWithFolderResponse> getAllMyQuestion(Long memberId) {
        List<MyQuestion> myQuestionList = myQuestionRepository.getAllMyQuestion(memberId);
        return myQuestionList.stream()
                .map(myQuestionMapper::myQuestionToMyQuestionByFolderResponse)
                .toList();
    }

    @Override
    public List<MyQuestionWithFolderResponse> getMyQuestionByKeyword(Long memberId, String keyword) {
        List<MyQuestion> myQuestionList = myQuestionRepository.getMyQuestionByKeyword(memberId, keyword);
        return myQuestionList.stream()
                .map(myQuestionMapper::myQuestionToMyQuestionByFolderResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MyAnswerAndVideoResponse getMyAnswerAndVideo(Long memberId, Long myQuestionId) {
        MyQuestion myQuestion = myQuestionRepository.findMyAnswerAndVideo(myQuestionId);
        if (myQuestion == null || !myQuestion.getMyQuestionFolder().getMember().getMemberId().equals(memberId)) {
            throw new MyQuestionNotFoundException();
        }
        return myQuestionMapper.myQuestionToMyAnswerAndVideoResponse(myQuestion);
    }

    @Override
    public void createMyQuestion(Long memberId, MyQuestionRequest myQuestionRequest) {

        if (myQuestionRequest.getQuestion() == null || myQuestionRequest.getQuestion().isBlank()) return;
        if (myQuestionRequest.getMyQuestionFolderId() == null) throw new MyQuestionFolderNotFoundException();

        MyQuestionFolder myQuestionFolder = myQuestionFolderRepository.findById(myQuestionRequest.getMyQuestionFolderId())
                .filter(folder -> folder.getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionFolderNotFoundException::new);

        MyQuestion myQuestion = myQuestionMapper.myQuestionRequestToEntity(myQuestionRequest);
        myQuestion.changeMyQuestionFolder(myQuestionFolder);

        if (myQuestionRequest.getCommonQuestionId() != null) {
            CommonQuestion commonQuestion = commonQuestionRepository.findById(myQuestionRequest.getCommonQuestionId())
                    .orElseThrow(CommonQuestionNotFoundException::new);
            myQuestion.saveCommonQuestion(commonQuestion);
        }

        myQuestionRepository.save(myQuestion);
    }

    @Override
    public void updateMyQuestion(Long memberId, Long myQuestionId, MyQuestionUpdateRequest myQuestionUpdateRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(myQuestionId)
                .filter(question -> question.getMyQuestionFolder().getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionNotFoundException::new);

        Optional.ofNullable(myQuestionUpdateRequest.getQuestion())
                .filter(question -> !question.isEmpty())
                .ifPresent(myQuestion::updateMyQuestion);
    }

    @Override
    public void updateMyAnswer(Long memberId, Long myQuestionId, MyAnswerUpdateRequest myAnswerUpdateRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(myQuestionId)
                .filter(question -> question.getMyQuestionFolder().getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionNotFoundException::new);

        Optional.ofNullable(myAnswerUpdateRequest.getAnswer())
                .ifPresent(myQuestion::updateMyAnswer);
    }

    @Override
    public void deleteMyQuestion(Long memberId, Long myQuestionId) {
        myQuestionRepository.findById(myQuestionId)
                .filter(question -> question.getMyQuestionFolder().getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionNotFoundException::new);
        myQuestionRepository.deleteById(myQuestionId);
    }

    @Override
    public void moveMyQuestion(Long memberId, MyQuestionMoveRequest myQuestionMoveRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(myQuestionMoveRequest.getMyQuestionId())
                .filter(question -> question.getMyQuestionFolder().getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionNotFoundException::new);

        MyQuestionFolder fromMyQuestionFolder = myQuestionFolderRepository.findById(myQuestionMoveRequest.getFromMyQuestionFolderId())
                .filter(folder -> folder.getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionFolderNotFoundException::new);

        if (!Objects.equals(myQuestion.getMyQuestionFolder(), fromMyQuestionFolder)) {
            throw new MyQuestionFolderNotMatchException();
        }

        MyQuestionFolder toMyQuestionFolder = myQuestionFolderRepository.findById(myQuestionMoveRequest.getToMyQuestionFolderId())
                .filter(folder -> folder.getMember().getMemberId().equals(memberId))
                .orElseThrow(MyQuestionFolderNotFoundException::new);

        myQuestion.moveMyQuestionFolder(fromMyQuestionFolder, toMyQuestionFolder);
    }
}
