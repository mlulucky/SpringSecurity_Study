package com.example.todolist_backend.service;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CreateJwtTest {

     @Value("${jwt.token.secret}")
    private String secretKeyPlain;

    @Test
    void 시크릿키_존재_확인() {
        assertThat(secretKeyPlain).isNotNull();
    }

    @Test
    @DisplayName("sercretKey 원문으로 hmac 암호화 알고리즘에 맞는 SecretKey 객체를 만들 수 있다.")
    void t2() {
        // 키를 Base64 인코딩
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        // Base64 인코딩된 키를 이용하여 SecretKey 객체를 만든다.
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
        System.out.println("secretKey = " + secretKey);
        assertThat(secretKey).isNotNull();
    }

    @Test
    void generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
        keyGenerator.init(512); // 512 비트 키 생성
        SecretKey generatedKey = keyGenerator.generateKey();
        System.out.println("generatedKey = " + generatedKey);
    }

    @Test
    void encodeSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
        keyGenerator.init(512); // 512 비트 키 생성
        SecretKey generatedKey = keyGenerator.generateKey();
        byte[] keyBytes = generatedKey.getEncoded();
        System.out.println("keyBytes = " + keyBytes);
    }


    @Test
    void check(){
        byte[] keyBytes = new byte[32]; // 32 바이트 길이의 키 생성
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        SecretKeySpec secretkey = new SecretKeySpec(keyBytes, "HS512");
        // 시크릿 키를 Base64로 인코딩하여 출력
        String encodedKey = Base64.getEncoder().encodeToString(secretkey.getEncoded());
        System.out.println("Encoded Secret Key: " + encodedKey);

        System.out.println("secretkey = " + secretkey);
    }

}

