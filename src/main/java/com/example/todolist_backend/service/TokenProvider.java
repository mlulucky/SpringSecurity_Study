package com.example.todolist_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    // JWT 생성 및 검증을 위한 키
    private static final String SECURITY_KEY = "jwtSecretKey";

    // JWT 토큰 생성하는 메서드
    public String create(String account) {
        // 만료날짜 현재시간 + 1시간 으로 설정
        Date experTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        // JWT 를 생성
        return Jwts.builder()
                // 암호화에 사용될 알고리즘, 키
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY)
                // JWT 제목, 생성일, 만료일 설정
                .setSubject(account).setIssuedAt(new Date()).setExpiration(experTime)
                // 생성
                .compact();
    }

    // JWT 검증
    public String validate(String token) {
        // 매개변수로 받은 token 을 키를 사용해서 복호화(디코딩_파싱)
        Claims claims = Jwts.parser()
                .setSigningKey(SECURITY_KEY)
                .parseClaimsJws(token)
                .getBody();
        // 복호화된 토큰의 payload 에서 제목(subject)을 가져옴
        return claims.getSubject();
    }


}
