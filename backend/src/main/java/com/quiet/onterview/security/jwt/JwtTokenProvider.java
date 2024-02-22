package com.quiet.onterview.security.jwt;

import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.security.exception.SecurityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateAccessToken(String email) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(1).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(21).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return !claims.getExpiration().before(new Date());
        } catch(ExpiredJwtException expiredJwtException) {
            return false;
        }
    }

    public String getEmail(String accessToken) throws SecurityException {
        if (!isValidToken(accessToken)) {
            throw new SecurityException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        String email = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        return email;
    }
}