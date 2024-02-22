package com.quiet.onterview.member.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.member.dto.request.MemberModifyNicknameRequest;
import com.quiet.onterview.member.dto.request.MemberWithdrawRequest;
import com.quiet.onterview.member.dto.response.MemberModifyNicknameResponse;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.service.MyQuestionFolderService;
import com.quiet.onterview.security.jwt.JwtTokenProvider;
import com.quiet.onterview.member.dto.request.MemberLoginRequest;
import com.quiet.onterview.member.dto.response.MemberLoginResponse;
import com.quiet.onterview.member.dto.request.MemberModifyPasswordRequest;
import com.quiet.onterview.member.dto.request.MemberSignupRequest;
import com.quiet.onterview.member.dto.response.MemberTokenResponse;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.mapper.MemberMapper;
import com.quiet.onterview.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = {Exception.class})
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyQuestionFolderService myQuestionFolderService;

    public void signUpByEmail(MemberSignupRequest memberSignupRequest) {
        if(!isEmailAvailable(memberSignupRequest.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_DUPLICATED);
        }
        if(!isNicknameAvailable(memberSignupRequest.getNickname())) {
            throw new BaseException(ErrorCode.NICKNAME_DUPLICATED);
        }
        if(!isPasswordCorrespond(memberSignupRequest.getPassword(), memberSignupRequest.getConfirm())) {
            throw new BaseException(ErrorCode.PASSWORD_CANNOT_CONFIRM);
        }
        Member member = memberRepository.save(memberMapper.memberSignupRequestToMember(memberSignupRequest));
        updatePassword(member.getMemberId(), member.getPassword());

        myQuestionFolderService.createMyQuestionFolder(member.getMemberId(),new MyQuestionFolderRequest("기본 폴더"));
    }

    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
        if(isEmailAvailable(memberLoginRequest.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_NOT_EXISTS);
        }
        Member member = memberRepository.findByEmail(memberLoginRequest.getEmail()).get();
        if(!passwordEncoder.encode(memberLoginRequest.getPassword()).equals(member.getPassword())) {
            throw new BaseException(ErrorCode.PASSWORD_NOT_MATCHES);
        }
        String accessToken = jwtTokenProvider.generateAccessToken(memberLoginRequest.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(memberLoginRequest.getEmail());
        return memberMapper.memberToMemberLoginResponse(member, accessToken, refreshToken);
    }

    public void modifyPassword(Long memberId, MemberModifyPasswordRequest memberModifyPasswordRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTS));
        if(!passwordEncoder.matches(memberModifyPasswordRequest.getOriginal(), member.getPassword())) {
            throw new BaseException(ErrorCode.PASSWORD_NOT_MATCHES);
        }
        if(!isPasswordCorrespond(memberModifyPasswordRequest.getPassword(), memberModifyPasswordRequest.getConfirm())) {
            throw new BaseException(ErrorCode.PASSWORD_CANNOT_CONFIRM);
        }
        updatePassword(memberId, memberModifyPasswordRequest.getPassword());
    }

    public MemberTokenResponse remakeMemberToken(String accessToken, String refreshToken) {
        if(jwtTokenProvider.isValidToken(accessToken)) {
            throw new BaseException(ErrorCode.ACCESS_TOKEN_NOT_EXPIRED);
        }
        if(!jwtTokenProvider.isValidToken(refreshToken)) {
            throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        String email = jwtTokenProvider.getEmail(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(email);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(email);
        return MemberTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void withdrawUser(Long memberId, MemberWithdrawRequest memberWithdrawRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_EXISTS));
        if(!passwordEncoder.matches(memberWithdrawRequest.getPassword(), member.getPassword())) {
            throw new BaseException(ErrorCode.PASSWORD_NOT_MATCHES);
        }
        memberRepository.deleteById(memberId);
    }

    public boolean isEmailAvailable(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    public boolean isNicknameAvailable(String nickname) {
        return memberRepository.findByNickname(nickname).isEmpty();
    }

    public boolean isPasswordCorrespond(String password, String confirm) {
        return password.equals(confirm);
    }

    public int updatePassword(Long userId, String password) {
        return memberRepository.updatePassword(userId, passwordEncoder.encode(password));
    }

    public MemberModifyNicknameResponse modifyNickname(Long memberId,
            MemberModifyNicknameRequest memberModifyNicknameRequest) {
        String nickname = memberModifyNicknameRequest.getNickname();
        if((nickname==null)) {
            throw new BaseException(ErrorCode.REQUIRED_VALUE_NOT_EXISTS);
        }
        if(!isNicknameAvailable(nickname)) {
            throw new BaseException(ErrorCode.NICKNAME_DUPLICATED);
        }
        memberRepository.updateNickname(memberId, nickname);
        return MemberModifyNicknameResponse.builder()
                .nickname(nickname)
                .build();
    }
}
