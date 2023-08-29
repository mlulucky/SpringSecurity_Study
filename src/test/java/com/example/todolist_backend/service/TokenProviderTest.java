package com.example.todolist_backend.service;

import com.example.todolist_backend.controller.UserController;
import com.example.todolist_backend.repository.RefreshTokenRepository;
import com.example.todolist_backend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

// @WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc // 모의 HTTP 요청 및 응답 객체를 제공
@TestPropertySource(locations = "classpath:application-test.properties")
class TokenProviderTest {
    @Autowired
    MockMvc mockMvc;

    //@MockBean
    @Autowired
    private TokenProvider tokenProvider;
    @MockBean
    ToDoService toDoService;
    @MockBean
    UserController userController;
    @MockBean
    RefreshTokenRepository refreshTokenRepository;

    @Test
    @WithMockUser
    void createToken() {
        Long userId = 1L;
        String token = tokenProvider.create(userId);
        // String account = "user1";
//        String token = tokenProvider.create(account);
        System.out.println("token = " + token);
    }

    @Test
    void validate() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY5MzI4OTgyMCwiZXhwIjoxNjkzMjkzNDIwfQ.zBUbca5KqtuK5k4rzVevLfWuidVN8pgLpeXJL2H_IQg";
        String validate = tokenProvider.validate(token);
        System.out.println("validate = " + validate); // user1
    }

    @Test
    void createRefreshToken() {
        String refreshToken = tokenProvider.createRefreshToken();
        System.out.println("refreshToken = " + refreshToken); // eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtbHVja3kiLCJpYXQiOjE2OTMyOTA5NDYsImV4cCI6MTY5MzM3NzM0Nn0.1mZA3oYOkSXIBVcoZzkj6P9iimWuBkukhg9iHlaf0CmjRjW83wZZfd4Gd0c6cdxwlMHgk9FmTsCVzTUwNF9XqA
    }

    @Test
    void recreateAccessToken() throws JsonProcessingException {
     Long userId = 1L;
     String token = tokenProvider.create(userId);
     String newAccessToken = tokenProvider.recreateAccessToken(token);
     System.out.println("newAccessToken = " + newAccessToken);
}

    @Test
    void validateRefreshToken() {
    }

    @Test
    void decodeJwtPayloadSubject() throws JsonProcessingException {
        String subject = tokenProvider.decodeJwtPayloadSubject("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjkzMzIwMDA1LCJleHAiOjE2OTMzMjM2MDV9.me_uNt9m4AW6dov2yE4R0iSb7EbXTSKGdOGhdyZ3y_I");
        System.out.println("subject = " + subject);
    }
}