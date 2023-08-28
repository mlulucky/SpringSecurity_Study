package com.example.todolist_backend.service;

import com.example.todolist_backend.repository.RefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

// JWT(json web token) : 전자 서명이 된 토큰
// JSON 형태로 구성된 토큰
// jwt 토큰 = {header}.{payload}.{signature}
// header: typ(해당 토큰의 타입), alg(토큰을 서명하기 위해 사용된 해시 알고리즘)
// payload: sub(해당 토큰의 주인), iat(토큰이 발행된 시간), exp(토큰이 만료되는 시간)

// @PropertySource("classpath:jwt.yml")
@Service
public class TokenProvider {
    // JWT 생성 및 검증을 위한 키
    private String secretKey;
    private final long expirationMinutes;
    private final long refreshExpirationHours;
    private final String issuer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long reissueLimit;

    private final ObjectMapper objectMapper = new ObjectMapper(); // JWT 역직렬화를 위한 ObjectMapper
    public TokenProvider(
            @Value("${jwt.token.secret}") String secretKey,
            @Value("${expiration-minutes}") long expirationMinutes,
            @Value("${refresh-expiration-hours}") long refreshExpirationHours,
            @Value("${issuer}") String issuer,
            RefreshTokenRepository refreshTokenRepository
            ) {
        this.secretKey = secretKey;
        this.expirationMinutes = expirationMinutes;
        this.refreshExpirationHours= refreshExpirationHours;
        this.issuer = issuer;
        this.refreshTokenRepository = refreshTokenRepository;
        reissueLimit = refreshExpirationHours * 60 / expirationMinutes; // 재발급 한도
    }

    // JWT 토큰 생성하는 메서드
    public String create(String account) {
        // 만료날짜 현재시간 + 1시간 으로 설정
        Date experTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        // JWT 를 생성
        return Jwts.builder()
                // 암호화에 사용될 알고리즘, 키
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // JWT 제목, 생성일, 만료일 설정
                .setSubject(account).setIssuedAt(new Date()).setExpiration(experTime)
                // 생성
                .compact();
    }

    // JWT 검증
    public String validate(String token) {
        // 매개변수로 받은 token 을 키를 사용해서 복호화(디코딩_파싱)
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        // 복호화된 토큰의 payload 에서 제목(subject)을 가져옴
        return claims.getSubject();
    }

    // refresh 토큰생성
    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName())) // 토큰의 비밀키가 너무 짧은경우 에러발생 // 비밀키(secret key) 생성 - 필요 jjwt 라이브러리 참조 - https://colabear754.tistory.com
                .setIssuer(issuer) // 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // 발급시간
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS))) // 만료시간
                .compact(); // 생성
    }

    // accessToken 재발급 // 재발급 횟수를 +1하고 새로운 액세스 토큰을 반환
//    public String recreateAccessToken(String oldAccessToken) {
//        // String subject = decode
//    }

    // refresh 토큰 유효성 검사

    // 토큰 유효성검사 및 파싱

    //  JWT 를 복호화하고 데이터가 담겨있는 Payload 에서 Subject 를 반환 // 만료된 액세스 토큰을 복호화
    public String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8), // token.split("\\.")[1] == payload // "\\." == .점 (정규표현식)// jwt (header(헤더).payload(내용).signature(서명))
                Map.class).get("sub").toString(); // payload 의 sub : 토큰제목 // readValue(object, map) -> object 를 json 문자열로 파싱하여 map 형태로 반환
    }


}
