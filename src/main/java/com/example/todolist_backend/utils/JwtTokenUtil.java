package com.example.todolist_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

// util 패키지 - 클래스
// 보안, 문자열처리, 날짜 처리 등 특정 비즈니스 로직과 독립적인 기능
// 토큰을 만들어내는 것 자체는 비즈니스 로직과 관련이 없기 때문에 util 패키지에 들어가는 것
// 특정 개념과 독립적인 기능 + 다른 부분과 의존성이 없고 input parameter 만 갖고 단순한 처리만 하는 메소드들은 특히 정적(static) 메소드로 많이 구성
public class JwtTokenUtil {
    public static String createToken(String account, String key, long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("account", account);

        return Jwts.builder()
                .setClaims(claims) // claims 지정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 만든 날짜
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 만료 닐짜
                .signWith(SignatureAlgorithm.HS256, key) // signWith(알고리즘방식, key) : key 암호화
                .compact();
    }
}

