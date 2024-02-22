package com.quiet.onterview.security;

import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.security.exception.SecurityException;
import com.quiet.onterview.security.exception.SecurityExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 해당 entry point는 security config에서 exceptionHandling 부분에 entry point로 등록해서 사용한다.
 * 이 entry point의 역할은 인증 되지 않은 사용자가 자원에 접근하려 할 때 해당 entry point로 오게 되어 에러가 던지거나 redirect하는 등의 행동을 하게 된다.
 */
@Component
@RequiredArgsConstructor
public class SecurityEntryPoint implements AuthenticationEntryPoint {

    private final SecurityExceptionHandler securityExceptionHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        securityExceptionHandler.handleSecurityError(response, new SecurityException(ErrorCode.ACCESS_TOKEN_NOT_RECEIVED));
    }
}
