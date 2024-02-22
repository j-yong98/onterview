package com.quiet.onterview.interview.dto.request;

import com.quiet.onterview.interview.entity.QuestionType;
import com.quiet.onterview.interview.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRoomRequest {

    private QuestionType questionType;
    private RoomType roomType;
    private int numToSelect = 5;
    private List<Long> memberIdList = new ArrayList<>();
}
