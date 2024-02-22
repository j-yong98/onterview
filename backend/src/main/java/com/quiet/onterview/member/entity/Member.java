package com.quiet.onterview.member.entity;

import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.entity.Comment;
import com.quiet.onterview.community.entity.Likes;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.Interviewee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@ToString
@Builder
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NICKNAME", unique = true, nullable = true)
    private String nickname;

    @Column(name = "IMAGE_URL", nullable = true)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "likesPrimaryKey.member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<Interviewee> intervieweeList = new ArrayList<>();

    public Member(long memberId, String email, String password, String nickname, Object imageUrl, ArrayList<Article> articleList, ArrayList<Likes> likesList) {
    }

    public void addInterviewee(Interviewee interviewee) {
        interviewee.setMember(this);
        intervieweeList.add(interviewee);
    }
}
