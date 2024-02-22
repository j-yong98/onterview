package com.quiet.onterview.security.jwt;


import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityMemberAuthentication;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.security.exception.SecurityException;
import com.quiet.onterview.security.exception.SecurityExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 해당 필터는 JWT을 가지고 해당 사용자를 인증하는 필터이다.
 * 여기서 OncePerRequestFilter는 기존 Filter(요청이 들어온 시점과 나가는 시점)와 다르게 초반에 요청이 들어왔을 때 한번만 검증한다는 특징이 있는 Filter이다.
 * 인증이 된 사용자는 SecurityContextHolder에 보관한다.
 */
@RequiredArgsConstructor
public class JwtDecoderFilter extends OncePerRequestFilter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final SecurityExceptionHandler securityExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String receivedToken = request.getHeader(AUTHORIZATION_HEADER);

        if(receivedToken==null) {
            filterChain.doFilter(request,response);
            return;
        }

        try {
            String email = jwtTokenProvider.getEmail(receivedToken);
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new SecurityException(ErrorCode.EMAIL_NOT_EXISTS));
            SecurityUser securityUser = new SecurityUser(member);
            SecurityContextHolder.getContext().setAuthentication(new SecurityMemberAuthentication(securityUser));
            filterChain.doFilter(request,response);
        } catch (AuthenticationException e) {
            securityExceptionHandler.handleSecurityError(response, e);
        }
    }
}
