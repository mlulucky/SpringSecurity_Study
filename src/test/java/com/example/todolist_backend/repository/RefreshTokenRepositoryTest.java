package com.example.todolist_backend.repository;

import com.example.todolist_backend.controller.UserController;
import com.example.todolist_backend.domain.RefreshToken;
import com.example.todolist_backend.service.ToDoService;
import com.example.todolist_backend.service.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class RefreshTokenRepositoryTest {
    @MockBean
    ToDoService toDoService;
    @MockBean
    UserController userController;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenRepository repository;
    @Test
    void findByUserIdAndReissueCountLessThan() {
        Long tokenSubject = 28L; // 실제 db 테이블에 저장된 유저id
         repository.findByUserIdAndReissueCountLessThan(tokenSubject, tokenProvider.getReissueLimit())
        .orElseThrow(()-> {throw new ExpiredJwtException(null, null, "리프레시 토큰이 만료되었습니다.");});
    }

//    @Test
//    void validateRefreshToken() {
//        // given
//        Long tokenSubject = 28L;
//        String requestRefreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJtbHVja3kiLCJpYXQiOjE2OTM0NDI5NzMsImV4cCI6MTY5MzUyOTM3M30.vbVvBp25OQDPFV268cKjRzn-EV6uxmYovH8wKsc075vYiTx-2YCam7y9RgWoo5ZQI1rxplYjpNPed1je2HK1rg";
//        tokenProvider.validateAndParseToken(requestRefreshToken);
//
//        // when
//        repository.findByUserIdAndReissueCountLessThan(tokenSubject, tokenProvider.getReissueLimit())
//        .filter(refreshToken -> refreshToken.validateRefreshToken(requestRefreshToken)) // RefreshToken 메서드 - validateRefreshToken // 저장소에 저장된 유저의 리프레시 토큰과 요청 들어온 리프레시토큰을 유효성검사
//          .orElseThrow(()->new ExpiredJwtException(null, null, "리프레시 토큰이 만료되었습니다."));
//
//    }
}