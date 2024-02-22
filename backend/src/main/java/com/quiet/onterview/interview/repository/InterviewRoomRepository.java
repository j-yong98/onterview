package com.quiet.onterview.interview.repository;

import com.quiet.onterview.interview.entity.InterviewRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterviewRoomRepository extends JpaRepository<InterviewRoom, Long> {

    @Query("SELECT ie.interviewRoom FROM Interviewee ie WHERE ie.member.memberId = :memberId AND ie.interviewRoom.roomType = 'SINGLE'")
    Page<InterviewRoom> findSingleInterviewRoom(Long memberId, Pageable pageable);

    @Query("SELECT ie.interviewRoom FROM Interviewee ie WHERE ie.member.memberId = :memberId AND ie.interviewRoom.roomType = 'MULTI'")
    Page<InterviewRoom> findMultiInterviewRoom(Long memberId, Pageable pageable);
//
//    @Query("SELECT ir FROM InterviewRoom ir WHERE ir.member.memberId = :memberId AND ir.roomType = 'SINGLE'")
//    List<InterviewRoom> findSingleInterviewRoomList(Long memberId);
//
//    @Query("SELECT ir FROM InterviewRoom ir WHERE ir.member.memberId = :memberId AND ir.roomType = 'MULTI'")
//    List<InterviewRoom> findMultiInterviewRoomList(Long memberId);
//
//    @Query("SELECT ir FROM InterviewRoom ir WHERE ir.member.memberId = :memberId AND ir.interviewRoomId = :interviewRoomId")
//    InterviewRoom findInterviewRoomDetail(Long memberId, Long interviewRoomId);
}
