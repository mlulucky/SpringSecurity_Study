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
        Long userId = 28L;
        String token = tokenProvider.create(userId);
        System.out.println("token = " + token);
        String newAccessToken = tokenProvider.recreateAccessToken(token);
        System.out.println("newAccessToken = " + newAccessToken);
}

//    @Test
//    void validateRefreshToken() throws JsonProcessingException {
//        Long userId = 28L;
//        // 28 유저가 가지고 있는 리프레시토큰 // 토큰 검사할때 유저 -> 서버에 요청하는 리프레시토큰(유저가 가지고 있는 토큰)과 db 저장된 유저의 리프레시토큰을 비교
//        String requestRefreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtbHVja3kiLCJpYXQiOjE2OTM0NDI5NzMsImV4cCI6MTY5MzUyOTM3M30.vbVvBp25OQDPFV268cKjRzn-EV6uxmYovH8wKsc075vYiTx-2YCam7y9RgWoo5ZQI1rxplYjpNPed1je2HK1rg";
//        String token = tokenProvider.create(userId);
//        Jws<Claims> ParseToken = tokenProvider.validateAndParseToken(token);
//        System.out.println("ParseToken = " + ParseToken);
//        tokenProvider.validateRefreshToken(requestRefreshToken, token);
//    }

    @Test
    void decodeJwtPayloadSubject() throws JsonProcessingException {
        String subject = tokenProvider.decodeJwtPayloadSubject("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkzMzIwMDA1LCJleHAiOjE2OTMzMjM2MDV9.me_uNt9m4AW6dov2yE4R0iSb7EbXTSKGdOGhdyZ3y_I");
        System.out.println("subject = " + subject);
    }
}