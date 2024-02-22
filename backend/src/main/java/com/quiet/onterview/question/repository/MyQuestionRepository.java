package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.MyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MyQuestionRepository extends JpaRepository<MyQuestion, Long> {

    @Query("SELECT mq FROM MyQuestion mq WHERE mq.myQuestionId = :myQuestionId")
    MyQuestion findMyAnswerAndVideo(@Param("myQuestionId") Long myQuestionId);

    @Query("SELECT mq FROM MyQuestion mq WHERE mq.myQuestionFolder.member.memberId = :memberId")
    List<MyQuestion> getAllMyQuestion(Long memberId);

    @Query("SELECT mq FROM MyQuestion mq WHERE mq.myQuestionFolder.member.memberId = :memberId AND LOWER(mq.question) LIKE %:keyword%")
    List<MyQuestion> getMyQuestionByKeyword(Long memberId, @RequestParam String keyword);
}
