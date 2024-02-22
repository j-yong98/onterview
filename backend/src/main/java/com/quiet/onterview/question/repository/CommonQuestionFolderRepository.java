package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommonQuestionFolderRepository extends JpaRepository<CommonQuestionFolder, Long> {

    @Query("SELECT cqf FROM CommonQuestionFolder cqf")
    List<CommonQuestionFolder> findCommonQuestionByFolder();

    @Query("SELECT cqf.commonQuestionFolderId FROM CommonQuestionFolder cqf")
    List<Long> findCommonQuestionFolderIdList();

    @Query("SELECT cq FROM CommonQuestionFolder cqf " +
            "JOIN FETCH CommonQuestion cq ON cqf.commonQuestionFolderId = :folderId AND cq.commonQuestionFolder.commonQuestionFolderId = :folderId " +
            "WHERE cq.commonQuestion LIKE %:keyword%")
    List<CommonQuestion> findCommonQuestionByFolderByKeyword(Long folderId, String keyword);

    @Query("SELECT cqf FROM CommonQuestionFolder cqf WHERE cqf.commonQuestionFolderId = :commonQuestionFolderId")
    Optional<CommonQuestionFolder> findOneCommonQuestionFolderInfo(Long commonQuestionFolderId);

    @Query("SELECT cqf FROM CommonQuestionFolder cqf WHERE cqf.commonQuestionFolder = :commonQuestionFolderName")
    Optional<CommonQuestionFolder> findInterviewQuestionFolder(String commonQuestionFolderName);
}
