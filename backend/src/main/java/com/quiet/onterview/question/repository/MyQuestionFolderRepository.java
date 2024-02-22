package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyQuestionFolderRepository extends JpaRepository<MyQuestionFolder, Long> {

    @Query("SELECT mqf FROM MyQuestionFolder mqf WHERE mqf.member.memberId = :memberId")
    List<MyQuestionFolder> findMyQuestionByFolder(Long memberId);

    @Query("SELECT mqf.myQuestionFolderId FROM MyQuestionFolder mqf WHERE mqf.member.memberId = :memberId")
    List<Long> findMyQuestionFolderIdList(Long memberId);

    @Query("SELECT mq FROM MyQuestionFolder mqf " +
            "JOIN FETCH MyQuestion mq ON mqf.myQuestionFolderId = :folderId AND mq.myQuestionFolder.myQuestionFolderId = :folderId " +
            "WHERE mqf.member.memberId = :memberId AND mq.question LIKE %:keyword%")
    List<MyQuestion> findMyQuestionByFolderByKeyword(Long memberId, Long folderId, String keyword);

}