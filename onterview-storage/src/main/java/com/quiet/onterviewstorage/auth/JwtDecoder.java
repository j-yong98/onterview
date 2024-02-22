package com.quiet.onterviewstorage.auth;

import io.jsonwebtoken.*;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtDecoder {

    @Value("${jwt.secret}")
    private String secretKey;

    private Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException e) {
            log.info("token is not trusted");
            throw new JwtException("token is not trusted");
        } catch (MalformedJwtException e) {
            log.info("token is malformed");
            throw new JwtException("token is malformed");
        } catch (UnsupportedJwtException e) {
            log.info("token is unsupported");
            throw new JwtException("token is unsupported");
        } catch (ExpiredJwtException e) {
            log.info("token is expired");
            throw new JwtException("token is expired");
        } catch (IllegalArgumentException e) {
            log.info("token is Illegal");
            throw new JwtException("token is empty");
        }
    }

    public boolean validateToken(String token) {
        return getClaims(token)
                .getBody()
                .getExpiration()
                .after(new Date());
    }
}