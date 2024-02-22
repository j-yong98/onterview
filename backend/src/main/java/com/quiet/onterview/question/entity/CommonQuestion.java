package com.quiet.onterview.question.entity;

import com.quiet.onterview.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "COMMON_QUESTION")
public class CommonQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_QUESTION_ID")
    private Long commonQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COMMON_QUESTION_FOLDER_ID")
    private CommonQuestionFolder commonQuestionFolder;

    @Column(name = "COMMON_QUESTION")
    private String commonQuestion;

    public void changeCommonQuestionFolder(CommonQuestionFolder commonQuestionFolder) {
        this.commonQuestionFolder = commonQuestionFolder;
        commonQuestionFolder.getCommonQuestionList().add(this);
    }

    public void updateCommonQuestion(String commonQuestion) { this.commonQuestion = commonQuestion; }

}
