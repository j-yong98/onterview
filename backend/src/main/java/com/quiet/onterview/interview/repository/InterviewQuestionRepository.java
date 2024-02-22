package com.quiet.onterview.interview.repository;

import com.quiet.onterview.interview.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

}
