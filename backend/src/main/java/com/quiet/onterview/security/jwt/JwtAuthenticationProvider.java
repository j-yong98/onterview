package com.quiet.onterview.security.jwt;

import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityMemberAuthentication;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.security.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new SecurityException(ErrorCode.EMAIL_NOT_EXISTS));
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new SecurityException(ErrorCode.PASSWORD_NOT_MATCHES);
        }
        return new SecurityMemberAuthentication(new SecurityUser(member));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
