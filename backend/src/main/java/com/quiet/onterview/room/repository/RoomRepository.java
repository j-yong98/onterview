package com.quiet.onterview.room.repository;

import static com.quiet.onterview.room.RoomStatus.*;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.exception.InterviewQuestionNotFoundException;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.exception.UserNotFoundException;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.room.Room;
import com.quiet.onterview.room.RoomStatus;
import com.quiet.onterview.room.exception.RoomNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public void generate(
            String sessionId,
            List<MatchUser> users,
            List<List<InterviewQuestionCreateResponse>> questions
    ) {
        Room room = new Room(users, questions);
        rooms.put(sessionId, room);
    }

    public Integer calc(String sessionId) {
        Room room = rooms.get(sessionId);
        return calc(room.getChecked());
    }

    private Integer calc(AtomicReferenceArray<Boolean> src) {
        int count = 0;
        for (int i = 0; i < src.length(); i++) {
            if (src.get(i)) {
                count++;
            }
        }
        return count;
    }

    public RoomStatus ping(String sessionId) {
        return rooms.get(sessionId).ping() == 0 ? CHECK : null;
    }

    public RoomStatus start(String sessionId, Integer idx) {
        return rooms.get(sessionId).count(idx) ? START : null;
    }

    public RoomStatus enter(String sessionId, Integer idx) {
        return rooms.get(sessionId).count(idx) ? ENTER : null;
    }

    public RoomStatus proceeding(String sessionId, RoomStatus roomStatus, Integer idx) {
        boolean count = rooms.get(sessionId).count(idx);
        if (allFinish(sessionId) && count) {
            return END;
        }
        return count ? FINISH : roomStatus;
    }

    private boolean allFinish(String sessionId) {
        Room room = rooms.get(sessionId);
        return room.getQuestions().stream()
                .findFirst()
                .orElseThrow(InterviewQuestionNotFoundException::new)
                .size() == room.getQuestionIdx();
    }

    public Boolean leave(String sessionId, Integer idx) {
        Room room = Optional.ofNullable(rooms.get(sessionId))
                .orElseThrow(RoomNotFoundException::new);
        try {
            rooms.get(sessionId).leave(idx);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
        return room.isAllLeave();
    }

    public void remove(String sessionId) {
        try {
            rooms.remove(sessionId);
        } catch (Exception e) {
            throw new RoomNotFoundException();
        }
    }

    public String findRoomByUser(String user) {
        return rooms.keySet().stream()
                .filter(sessionId -> rooms.get(sessionId).getUsers()
                        .contains(MatchUser.builder().principal(user).build()))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }


    public int findIndexByUser(String sessionId, String user) {
        int idx = rooms.get(sessionId)
                .getUsers()
                .indexOf(
                        MatchUser.builder().principal(user).build()
                );
        if (idx == -1) {
            throw new UserNotFoundException();
        }
        return idx;
    }

    public Room findRoomByKey(String sessionId) {
        return Optional.ofNullable(rooms.get(sessionId)).orElseThrow(RoomNotFoundException::new);
    }

    public List<Integer> shuffle(String sessionId) {
        AtomicReferenceArray<Boolean> leave = rooms.get(sessionId).getIsLeave();
        List<Integer> orders = new ArrayList<>();
        for (int i = 0; i < leave.length(); i++) {
            if (leave.get(i)) {
                continue;
            }
            orders.add(i);
        }
        Collections.shuffle(orders);
        return orders;
    }

    public CommonQuestionResponse getQuestion(String sessionId) {
        return rooms.get(sessionId).getQuestion();
    }
}
