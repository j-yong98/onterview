package com.quiet.onterviewstorage.auth;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtDecoderFilter extends OncePerRequestFilter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String receivedToken = request.getHeader(AUTHORIZATION_HEADER);

        try {
            if ((
                    !request.getMethod().equals("DELETE") ||
                            request.getRequestURI().startsWith("/api-file/file")
            ) || jwtDecoder.validateToken(receivedToken)) {
                filterChain.doFilter(request, response);
            }
        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(e.getMessage());
        }
    }
}
