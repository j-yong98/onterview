package com.quiet.onterview.question.entity;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "MY_QUESTION_FOLDER")
public class MyQuestionFolder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_QUESTION_FOLDER_ID")
    private Long myQuestionFolderId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "myQuestionFolder", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<MyQuestion> myQuestionList = new ArrayList<>();

    @Column(name = "MY_QUESTION_FOLDER", nullable = false)
    private String myQuestionFolder;

    public void updateMyQuestionFolder(String myQuestionFolder) {
        this.myQuestionFolder = myQuestionFolder;
    }
}
