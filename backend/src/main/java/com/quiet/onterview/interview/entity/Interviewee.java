package com.quiet.onterview.interview.entity;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "INTERVIEWEE")
public class Interviewee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEWEE_ID")
    private Long intervieweeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "INTERVIEW_ROOM_ID")
    private InterviewRoom interviewRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "FEEDBACK")
    private String feedback;

    @OneToMany(mappedBy = "interviewee", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<InterviewQuestion> interviewQuestionList = new ArrayList<>();

    public void addInterviewQuestion(InterviewQuestion interviewQuestion) {
        interviewQuestion.setInterviewee(this);
        interviewQuestionList.add(interviewQuestion);
    }
}
