package com.quiet.onterview.question.entity;

import com.quiet.onterview.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "COMMON_QUESTION_FOLDER")
public class CommonQuestionFolder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_QUESTION_FOLDER_ID")
    private Long commonQuestionFolderId;

    @Column(name = "COMMON_QUESTION_FOLDER")
    private String commonQuestionFolder;

    @OneToMany(mappedBy = "commonQuestionFolder", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<CommonQuestion> commonQuestionList = new ArrayList<>();

    public void updateCommonQuestionFolder(String commonQuestionFolder) {
        this.commonQuestionFolder = commonQuestionFolder;
    }
}