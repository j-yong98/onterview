package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.CommonQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommonQuestionRepository extends JpaRepository<CommonQuestion, Long> {

    @Query("SELECT cq FROM CommonQuestion cq")
    List<CommonQuestion> getAllCommonQuestion();

    @Query("SELECT cq FROM CommonQuestion cq WHERE LOWER(cq.commonQuestion) LIKE %:keyword%")
    List<CommonQuestion> getCommonQuestionByKeyword(@RequestParam String keyword);

}
