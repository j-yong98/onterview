package com.quiet.onterview.matching.repository;

import com.quiet.onterview.interview.entity.QuestionType;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.exception.UserNotFoundException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MatchRepository {

    private int roomSize;
    private ConcurrentMap<Integer, ArrayDeque<MatchUser>> waitingRooms;

    @PostConstruct
    private void init() {
        waitingRooms = new ConcurrentHashMap<>();
        roomSize = QuestionType.values().length;
        IntStream.range(1, roomSize + 1)
                .forEach(i -> waitingRooms.put(i, new ArrayDeque<>()));
    }

    public boolean isExist(Integer roomId, MatchUser matchUser) {
        return waitingRooms.get(roomId).contains(matchUser);
    }

    public void enter(Integer roomId, MatchUser matchUser) {
        waitingRooms.get(roomId).add(matchUser);
    }

    public boolean isMatch(Integer roomId, Integer matchCount) {
        return waitingRooms.get(roomId).size() >= matchCount;
    }

    public int leave(String user) {
        int roomId = findRoom(user);
        waitingRooms.get(roomId).removeIf(matchUser -> matchUser.getPrincipal().equals(user));
        return waitingRooms.get(roomId).size();
    }

    private int findRoom(String user) {
        return IntStream.rangeClosed(1, roomSize)
                .filter(i -> waitingRooms.get(i)
                        .contains(MatchUser.builder().principal(user).build()))
                .findFirst().orElseThrow(UserNotFoundException::new);
    }

    public List<MatchUser> getUsers(Integer roomId, Integer matchCount) {
        List<MatchUser> result = new ArrayList<>();
        Deque<MatchUser> users = waitingRooms.get(roomId);
        while (matchCount-- > 0) {
            result.add(users.pollFirst());
        }
        return result;
    }
}
