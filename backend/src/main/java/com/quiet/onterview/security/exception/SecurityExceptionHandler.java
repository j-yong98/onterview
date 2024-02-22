package com.quiet.onterview.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiet.onterview.common.BaseExceptionResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SecurityExceptionHandler {

    public void handleSecurityError(HttpServletResponse response, AuthenticationException exception) {
        SecurityException securityException = (SecurityException) exception;
        response.setStatus(securityException.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(BaseExceptionResponse.builder()
                    .errorCode(securityException.getHttpStatus().value())
                    .errorMessage(securityException.getMessage())
                    .build());
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}