package com.quiet.onterview.question.entity;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.video.entity.Video;
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
@Table(name = "MY_QUESTION")
public class MyQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_QUESTION_ID")
    private Long myQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MY_QUESTION_FOLDER_ID", nullable = false)
    private MyQuestionFolder myQuestionFolder;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWER")
    private String answer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COMMON_QUESTION_ID")
    private CommonQuestion commonQuestion;

    @OneToMany(mappedBy = "myQuestion", cascade = CascadeType.PERSIST, orphanRemoval = true)
    final List<Video> videoList = new ArrayList<>();

    public void changeMyQuestionFolder(MyQuestionFolder myQuestionFolder) {
        this.myQuestionFolder = myQuestionFolder;
        myQuestionFolder.getMyQuestionList().add(this);
    }

    public void moveMyQuestionFolder(MyQuestionFolder fromMyQuestionFolder, MyQuestionFolder toMyQuestionFolder) {
        fromMyQuestionFolder.getMyQuestionList().remove(this);
        this.myQuestionFolder = toMyQuestionFolder;
        myQuestionFolder.getMyQuestionList().add(this);
    }

    public void saveCommonQuestion(CommonQuestion commonQuestion) {
        this.commonQuestion = commonQuestion;
    }
    public void updateMyQuestion(String question) { this.question = question; }
    public void updateMyAnswer(String answer) { this.answer = answer; }

}
