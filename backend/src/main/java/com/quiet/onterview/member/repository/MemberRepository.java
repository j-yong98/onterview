package com.quiet.onterview.member.repository;

import com.quiet.onterview.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);

    @Modifying
    @Query("update Member m set m.password = :password where m.memberId = :userId")
    int updatePassword(Long userId, String password);

    @Modifying
    @Query("update Member m set m.nickname = :nickname where m.memberId = :userId")
    int updateNickname(Long userId, String nickname);
}
