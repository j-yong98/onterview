package com.quiet.onterview.community.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.video.entity.Video;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "ARTICLE")
public class Article extends BaseEntity {

    @Id
    @Column(name = "ARTICLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "VIDEO_ID")
    private Video video;

    @Column(name="TITLE")
    private String title;

    @Column(name="CONTENT")
    private String content;

    @Column(name="LIKE_COUNT", nullable = false)
    @ColumnDefault("0")
    private Integer likeCount;

    @Column(name="COMMENT_COUNT", nullable = false)
    @ColumnDefault("0")
    private Integer commentCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();
}
