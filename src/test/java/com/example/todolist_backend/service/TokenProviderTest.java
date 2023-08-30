package com.example.todolist_backend.service;

import com.example.todolist_backend.controller.UserController;
import com.example.todolist_backend.repository.RefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
// @WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc // 모의 HTTP 요청 및 응답 객체를 제공
@TestPropertySource(locations = "classpath:application-test.properties")
class TokenProviderTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    ToDoService toDoService;

    @MockBean
    UserController userController;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.token.secret}") String secretKey;
    @Value("${expiration-minutes}") long expirationMinutes;
    @Value("${refresh-expiration-hours}") long refreshExpirationHours;
    @Value("${issuer}") String issuer;
    // long reissueLimit = refreshExpirationHours * 60 / 30;

    @Test
    @WithMockUser
    void createToken() {
        Long userId = 1L;
        String token = tokenProvider.create(userId);
        System.out.println("token = " + token);
    }



    @Test
    void validate() {
        Long userId = 28L;
        String token = tokenProvider.create(userId);
        String validate = tokenProvider.validate(token);
        System.out.println("validate = " + validate); // 28 (subject)
    }

    @Test
    void createRefreshToken() {
        String refreshToken = tokenProvider.createRefreshToken();
        System.out.println("refreshToken = " + refreshToken);
    }

    @Test
    void recreateAccessToken() throws JsonProcessingException {
        // recreateAccessToken 에러사항
        // 👀 reissueLimit 변수는  TokenProvider 클래스 멤버변수 이므로 테스트 클래스 TokenProviderTest 내에서 직접 접근할 수없다.
        // TokenProvider 객체를 생성하고 그 객체를 통해 접근해야한다. -> // 객체는 @Autowired 로 주입받았기 때문에, 객체 내 멤버필드 reissueLimit 만 변수 선언 해주기!
        // TokenProvider tokenProvider = new TokenProvider(secretKey, expirationMinutes, refreshExpirationHours, issuer, refreshTokenRepository);
        // long reissueLimit = refreshExpirationHours * 60 / 30;
        Long userId = 28L;
        String token = tokenProvider.create(userId);
        System.out.println("token = " + token);
        String newAccessToken = tokenProvider.recreateAccessToken(token);
        System.out.println("newAccessToken = " + newAccessToken);
}

    @Test
    void validateRefreshToken() throws JsonProcessingException {
     Long userId = 28L;
        String refreshToken = tokenProvider.createRefreshToken();
        String token = tokenProvider.create(userId);
        System.out.println("token = " + token);
        Jws<Claims> ParseToken = tokenProvider.validateAndParseToken(token);
        System.out.println("ParseToken = " + ParseToken);
        tokenProvider.validateRefreshToken(refreshToken, token);
    }

    @Test
    void decodeJwtPayloadSubject() throws JsonProcessingException {
        String subject = tokenProvider.decodeJwtPayloadSubject("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkzMzIwMDA1LCJleHAiOjE2OTMzMjM2MDV9.me_uNt9m4AW6dov2yE4R0iSb7EbXTSKGdOGhdyZ3y_I");
        System.out.println("subject = " + subject);
    }
}