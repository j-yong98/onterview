package com.quiet.onterview.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiet.onterview.member.dto.request.MemberLoginRequest;
import com.quiet.onterview.member.dto.response.MemberLoginResponse;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.security.exception.SecurityExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtAuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityExceptionHandler securityExceptionHandler;

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager,
            JwtTokenProvider jwtTokenProvider,
            SecurityExceptionHandler securityExceptionHandler) {
        this.authenticationManager = jwtAuthenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityExceptionHandler = securityExceptionHandler;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginRequest memberLoginRequest;

        try {
            memberLoginRequest = objectMapper.readValue(request.getInputStream(), MemberLoginRequest.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch(AuthenticationException e) {
            securityExceptionHandler.handleSecurityError(response,e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        SecurityUser securityMemberDetail = (SecurityUser) authResult.getPrincipal();
        String accessToken = jwtTokenProvider.generateAccessToken(securityMemberDetail.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(securityMemberDetail.getEmail());

        MemberLoginResponse.builder()
                .memberId(securityMemberDetail.getMemberId())
                .nickname(securityMemberDetail.getNickname())
                .email(securityMemberDetail.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = new ObjectMapper().writeValueAsString(
                MemberLoginResponse.builder()
                        .memberId(securityMemberDetail.getMemberId())
                        .nickname(securityMemberDetail.getNickname())
                        .email(securityMemberDetail.getEmail())
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build());
        response.getWriter().write(jsonResponse);
    }
}
