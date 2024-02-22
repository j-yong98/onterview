package com.quiet.onterview.interview.repository;

import com.quiet.onterview.interview.entity.Interviewee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervieweeRepository extends JpaRepository<Interviewee, Long> {

}
