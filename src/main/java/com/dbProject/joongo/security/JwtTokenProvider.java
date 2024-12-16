package com.dbProject.joongo.security;

import com.dbProject.joongo.global.LoginConst;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKeyString) {
        // SecretKey 객체를 생성 (HS256/HS512 방식에 맞는 키)
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // 토큰 생성
    public String createToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS512) // 최신 방식의 signWith
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            if (token == null || !token.startsWith(LoginConst.AUTH_HEADER_PREFIX)) {
                throw new IllegalArgumentException("Invalid Authorization header format");
            }
            String jwtToken = token.substring(7);
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired or invalid JWT token: {}", token);
            System.err.println("Token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Malformed JWT: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("Invalid signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument: " + e.getMessage());
        }
        return false;
    }

    // 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
