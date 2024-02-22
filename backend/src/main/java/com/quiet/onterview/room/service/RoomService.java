package com.quiet.onterview.room.service;

import static com.quiet.onterview.room.RoomStatus.ENTER;
import static com.quiet.onterview.room.RoomStatus.FINISH;
import static com.quiet.onterview.room.RoomStatus.LEAVE;
import static com.quiet.onterview.room.RoomStatus.SAVED;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.exception.TypeMismatchException;
import com.quiet.onterview.room.Room;
import com.quiet.onterview.room.RoomStatus;
import com.quiet.onterview.room.dto.request.UserRequestMessage;
import com.quiet.onterview.room.dto.response.RoomLeaveResponse;
import com.quiet.onterview.room.dto.response.RoomProgressResponse;
import com.quiet.onterview.room.dto.response.RoomProgressResponse.RoomProgressResponseBuilder;
import com.quiet.onterview.room.repository.RoomRepository;
import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.service.VideoService;
import com.quiet.onterview.websocket.MessageAnnounce;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final String ROOM_PREFIX = "/client/answer/";
    private final RoomRepository roomRepository;
    private final VideoService videoService;
    private final MessageAnnounce messageService;

    public void generate(
            String sessionId,
            List<MatchUser> users,
            List<List<InterviewQuestionCreateResponse>> questions
    ) {
        roomRepository.generate(sessionId, users, questions);
    }

    public void leave(String user) {
        String sessionId = roomRepository.findRoomByUser(user);
        int idx = roomRepository.findIndexByUser(sessionId, user);
        if (roomRepository.leave(sessionId, idx)) {
            roomRepository.remove(sessionId);
        }
        messageService.announceAll(ROOM_PREFIX + sessionId,
                RoomLeaveResponse.builder().type(LEAVE).idx(idx).build());
    }

    public void process(String sessionId, UserRequestMessage userRequestMessage) {
        RoomStatus roomStatus = userRequestMessage.getType();
        Integer idx = userRequestMessage.getIndex();
        RoomProgressResponseBuilder progressResponseBuilder = RoomProgressResponse.builder()
                .type(roomStatus)
                .number(idx);

        RoomStatus type = switch (userRequestMessage.getType()) {
            case ENTER -> roomRepository.enter(sessionId, idx);
            case START -> roomRepository.start(sessionId, idx);
            case PROCEEDING, TIMEOUT -> roomRepository.proceeding(sessionId, roomStatus, idx);
            case SAVE -> save(sessionId, userRequestMessage);
            case CHECK -> roomRepository.ping(sessionId);
            default -> throw new TypeMismatchException();
        };

        if (type == null) {
            return;
        }

        progressResponseBuilder.type(type);
        progressResponseBuilder.number(roomRepository.calc(sessionId));
        if (type == ENTER || type == FINISH) {
            progressResponseBuilder.orders(roomRepository.shuffle(sessionId));
            progressResponseBuilder.question(roomRepository.getQuestion(sessionId));
        } else if (type == SAVED) {
            progressResponseBuilder.number(null);
            progressResponseBuilder.index(idx);
        }
        messageService.announceAll(ROOM_PREFIX + sessionId, progressResponseBuilder.build());
    }

    private RoomStatus save(String sessionId, UserRequestMessage userRequestMessage) {
        Room room = roomRepository.findRoomByKey(sessionId);
        int userIndex = userRequestMessage.getIndex();

        AtomicInteger questionIndex = new AtomicInteger(0);
        List<InterviewQuestionCreateResponse> interviewQuestionCreateResponses = room.getQuestions()
                .get(userIndex);
        userRequestMessage.getVideos().forEach(video -> videoService.createVideoInformation(
                VideoInformationRequest.builder()
                        .interviewQuestionId(
                                interviewQuestionCreateResponses.get(
                                                questionIndex.getAndIncrement())
                                        .getInterviewQuestionId()
                        )
                        .title(video.getTitle())
                        .videoLength(video.getVideoLength())
                        .thumbnailInformation(video.getThumbnailUrl())
                        .videoInformation(video.getVideoUrl())
                        .build()
        ));
        return SAVED;
    }
}
