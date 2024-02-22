package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.entity.Interviewee;
import com.quiet.onterview.member.entity.Member;

public interface IntervieweeService {

     Interviewee createInterviewee(Member member);
}