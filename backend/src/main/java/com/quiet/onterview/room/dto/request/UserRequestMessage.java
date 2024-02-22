package com.quiet.onterview.room.dto.request;

import com.quiet.onterview.room.RoomStatus;
import java.util.List;
import lombok.Getter;

@Getter
public class UserRequestMessage {
    RoomStatus type;
    Integer index;
    List<UserVideoRequestMessage> videos;
}
