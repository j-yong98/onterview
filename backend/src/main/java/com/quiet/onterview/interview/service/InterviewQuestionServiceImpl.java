package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.Interviewee;
import com.quiet.onterview.interview.repository.InterviewQuestionRepository;
import com.quiet.onterview.question.entity.CommonQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewQuestionServiceImpl implements InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;

    @Override
    public InterviewQuestion createInterviewQuestion(Interviewee interviewee, CommonQuestion commonQuestion) {
        InterviewQuestion interviewQuestion = InterviewQuestion.builder()
                .interviewee(interviewee)
                .commonQuestion(commonQuestion)
                .build();
        return interviewQuestionRepository.save(interviewQuestion);
    }
}