package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.Interviewee;
import com.quiet.onterview.question.entity.CommonQuestion;

public interface InterviewQuestionService {

     InterviewQuestion createInterviewQuestion(Interviewee interviewee, CommonQuestion commonQuestion);
}