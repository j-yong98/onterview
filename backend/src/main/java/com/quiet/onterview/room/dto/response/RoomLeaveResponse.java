package com.quiet.onterview.room.dto.response;

import com.quiet.onterview.room.RoomStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomLeaveResponse {
    private RoomStatus type;
    private Integer idx;
}
