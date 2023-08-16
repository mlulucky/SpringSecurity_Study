package com.example.todolist_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

// util 패키지 - 클래스
// 보안, 문자열처리, 날짜 처리 등 특정 비즈니스 로직과 독립적인 기능
// 토큰을 만들어내는 것 자체는 비즈니스 로직과 관련이 없기 때문에 util 패키지에 들어가는 것
// 특정 개념과 독립적인 기능 + 다른 부분과 의존성이 없고 input parameter 만 갖고 단순한 처리만 하는 메소드들은 특히 정적(static) 메소드로 많이 구성
public class JwtTokenUtil { // 🌈 userService 에서 로그인시 jwt 토큰 발행에 사용
    public static String getUserName(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("account", String.class); // claimName 을 가져올때 String 타입으로 가져오겠다
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token) // setSigningKey() : 토큰 생성 비밀키 설정 // .parseClaimsJwt() : 주어진 토큰(token)을 분석. JWT 에 포함된 정보를 가져옴
                .getBody().getExpiration().before(new Date()); //  JWT 토큰의 만료 시간을 가져와서 현재 시간과 비교
    }
    public static String createToken(String account, String secretKey, long expireTimeMs) {
        Claims claims = Jwts.claims(); // 정보를 담는 // 일종의 map
        claims.put("account", account);

        return Jwts.builder()
                .setClaims(claims) // claims 지정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 만든 날짜
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) // 만료 닐짜
                .signWith(SignatureAlgorithm.HS256, secretKey) // signWith(알고리즘방식, key) : key 암호화
                .compact();
    }
}

