package com.quiet.onterview.video.repository;


import static com.quiet.onterview.interview.entity.QInterviewQuestion.interviewQuestion;
import static com.quiet.onterview.interview.entity.QInterviewRoom.interviewRoom;
import static com.quiet.onterview.interview.entity.QInterviewee.interviewee;
import static com.quiet.onterview.question.entity.QCommonQuestion.commonQuestion1;
import static com.quiet.onterview.question.entity.QMyQuestion.myQuestion;
import static com.quiet.onterview.question.entity.QMyQuestionFolder.myQuestionFolder1;
import static com.quiet.onterview.video.entity.QVideo.video;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import com.quiet.onterview.file.entity.QFileInformation;
import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VideoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<VideoInformationResponse> findAllInterviewVideoByMemberAndType(Long memberId,
            RoomType roomType, String keyword, Integer bookmark) {
        QFileInformation thumbnailUrl = new QFileInformation("thumbnailUrl");

        JPAQuery<VideoInformationResponse> query = queryFactory
                .select(getVideoDtoByType(roomType))
                .from(video)
                .leftJoin(video.thumbnailUrl, thumbnailUrl);

        joinByRoomType(query, roomType);

        query
                .where(
                        eqMemberId(roomType, memberId)
                                .and(eqRoomType(roomType))
                                .and(likeKeyword(keyword, roomType))
                                .and(eqBookmark(bookmark))
                );
        return query.fetch();
    }

    private static QBean<VideoInformationResponse> getVideoDtoByType(
            RoomType roomType) {
        return Projections.fields(VideoInformationResponse.class,
                video.videoId,
                roomType == RoomType.SELF ? video.myQuestion.myQuestionId.as("myQuestionId")
                        : video.interviewQuestion.interviewQuestionId.as("interviewQuestionId"),
                video.title,
                (roomType == RoomType.SELF ? video.myQuestion.question
                        : video.interviewQuestion.commonQuestion.commonQuestion).as("question"),
                Projections.fields(FileInformationResponse.class,
                        video.thumbnailUrl.originFilename,
                        video.thumbnailUrl.saveFilename).as("thumbnailUrl"),
                video.feedback,
                video.bookmark);
    }

    private void joinByRoomType(JPAQuery<VideoInformationResponse> query, RoomType roomType) {
        switch (roomType) {
            case SELF -> query
                    .leftJoin(video.myQuestion, myQuestion)
                    .leftJoin(myQuestion.myQuestionFolder, myQuestionFolder1);
            case MULTI, SINGLE -> query
                    .leftJoin(video.interviewQuestion, interviewQuestion)
                    .leftJoin(interviewQuestion.commonQuestion, commonQuestion1)
                    .leftJoin(interviewQuestion.interviewee, interviewee)
                    .leftJoin(interviewee.interviewRoom, interviewRoom);
        }
    }

    private BooleanExpression eqBookmark(Integer bookmark) {
        if (bookmark == null || bookmark == 0) {
            return null;
        }
        return video.bookmark.eq(true);
    }

    private BooleanExpression likeKeyword(String keyword, RoomType roomType) {
        if (keyword == null) {
            return null;
        }

        switch (roomType) {
            case SELF -> {
                return video.myQuestion.question.contains(keyword);
            }
            case SINGLE, MULTI -> {
                return video.interviewQuestion.commonQuestion.commonQuestion.contains(keyword);
            }
            default -> {
                throw new BaseException(ErrorCode.ROOM_NOT_FOUND);
            }
        }
    }

    private BooleanExpression eqRoomType(RoomType roomType) {
        if (roomType == RoomType.SELF) {
            return null;
        }
        return interviewRoom.roomType.eq(roomType);
    }

    private BooleanExpression eqMemberId(RoomType roomType, Long memberId) {
        if (roomType == RoomType.SELF) {
            return myQuestionFolder1.member.memberId.eq(memberId);
        }
        return interviewee.member.memberId.eq(memberId);
    }
}
