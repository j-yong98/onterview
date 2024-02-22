package com.quiet.onterview.video.entity;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.file.entity.FileInformation;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.question.entity.MyQuestion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "VIDEO")
public class Video extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIDEO_ID")
    private Long videoId;

    @Column(name = "TITLE")
    private String title;


    @Column(name = "VIDEO_LENGTH")
    private Long videoLength;

    @Column(name = "BOOKMARK")
    private Boolean bookmark = Boolean.FALSE;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "MY_QUESTION_ID")
    private MyQuestion myQuestion;

    @OneToOne
    @JoinColumn(name = "INTERVIEW_QUESTION_ID")
    private InterviewQuestion interviewQuestion;

    @OneToOne(fetch = LAZY, cascade = PERSIST, orphanRemoval = true)
    @JoinColumn(name = "VIDEO_URL")
    private FileInformation videoUrl;

    @OneToOne(cascade = PERSIST, orphanRemoval = true)
    @JoinColumn(name = "THUMBNAIL_URL")
    private FileInformation thumbnailUrl;

    @OneToMany(mappedBy = "video", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Article> articleList = new ArrayList<>();

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateBookmark(Boolean bookmark) {
        this.bookmark = bookmark;
    }

    public void updateFeedback(String feedback) {
        this.feedback = feedback;
    }
}
