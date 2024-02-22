package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.entity.Interviewee;
import com.quiet.onterview.interview.repository.IntervieweeRepository;
import com.quiet.onterview.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class IntervieweeServiceImpl implements IntervieweeService {

    private final IntervieweeRepository intervieweeRepository;

    @Override
    public Interviewee createInterviewee(Member member) {
        Interviewee interviewee = Interviewee.builder()
                .member(member)
                .interviewQuestionList(new ArrayList<>())
                .build();
        return intervieweeRepository.save(interviewee);
    }
}