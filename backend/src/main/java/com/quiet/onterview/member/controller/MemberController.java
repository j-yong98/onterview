package com.quiet.onterview.member.controller;

import com.quiet.onterview.member.dto.request.MemberModifyNicknameRequest;
import com.quiet.onterview.member.dto.request.MemberWithdrawRequest;
import com.quiet.onterview.member.dto.response.MemberDuplicateResponse;
import com.quiet.onterview.member.dto.request.MemberLoginRequest;
import com.quiet.onterview.member.dto.response.MemberLoginResponse;
import com.quiet.onterview.member.dto.request.MemberSignupRequest;
import com.quiet.onterview.member.dto.request.MemberModifyPasswordRequest;
import com.quiet.onterview.member.dto.response.MemberModifyNicknameResponse;
import com.quiet.onterview.member.dto.response.MemberTokenResponse;
import com.quiet.onterview.member.service.MemberService;
import com.quiet.onterview.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity signUpByEmail(@RequestBody MemberSignupRequest memberSignupRequest) {
        memberService.signUpByEmail(memberSignupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        return ResponseEntity.ok(memberService.login(memberLoginRequest));
    }

    @PatchMapping("/password")
    public ResponseEntity modifyPassword(@AuthenticationPrincipal SecurityUser user,
            @RequestBody MemberModifyPasswordRequest memberModifyPasswordRequest) {
        memberService.modifyPassword(user.getMemberId(), memberModifyPasswordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity withdrawMember(@AuthenticationPrincipal SecurityUser user,
            @RequestBody MemberWithdrawRequest memberWithdrawRequest) {
        memberService.withdrawUser(user.getMemberId(), memberWithdrawRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<MemberDuplicateResponse> checkNicknameAvailable(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(MemberDuplicateResponse.builder().isAvaliable(memberService.isNicknameAvailable(nickname)).build());
    }

    @GetMapping("/check/email")
    public ResponseEntity<MemberDuplicateResponse> checkEmailAvailable(@RequestParam("email") String email) {
        return ResponseEntity.ok(MemberDuplicateResponse.builder().isAvaliable(memberService.isEmailAvailable(email)).build());
    }

    @GetMapping("/token")
    public ResponseEntity<MemberTokenResponse> remakeMemberToken(
            @RequestHeader("AccessToken") String accessToken,
            @RequestHeader("RefreshToken") String refreshToken) {
        return ResponseEntity.ok(memberService.remakeMemberToken(accessToken, refreshToken));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<MemberModifyNicknameResponse> modifyUserNickname(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody MemberModifyNicknameRequest memberModifyNicknameRequest) {
        return ResponseEntity.ok(memberService.modifyNickname(user.getMemberId(), memberModifyNicknameRequest));
    }
}
