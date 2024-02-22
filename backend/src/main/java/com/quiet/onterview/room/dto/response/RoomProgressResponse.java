package com.quiet.onterview.room.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.room.RoomStatus;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class RoomProgressResponse {
    RoomStatus type;
    @JsonInclude(Include.NON_NULL)
    Integer index; //현재 진행 완료한 사람 수
    @JsonInclude(Include.NON_NULL)
    Integer number; //현재 진행 완료한 사람 수
    @JsonInclude(Include.NON_NULL)
    List<Integer> orders;
    @JsonInclude(Include.NON_NULL)
    CommonQuestionResponse question;
}
