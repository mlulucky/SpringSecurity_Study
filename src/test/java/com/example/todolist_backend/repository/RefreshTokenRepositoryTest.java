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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class RefreshTokenRepositoryTest {
    @MockBean
    ToDoService toDoService;
    @MockBean
    UserController userController;
    //@Autowired
    @MockBean
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenRepository repository;
    @Test
    void findByUserIdAndReissueCountLessThan() {
        Long tokenSubject = 28L; // 실제 db 테이블에 저장된 유저id
        long reissueLimit = 24*60 / 30; // 재발급횟수
        Optional<RefreshToken> refreshToken = repository.findByUserId(tokenSubject);
        // Optional<RefreshToken> refreshToken = repository.findByUserIdAndReissueCountLessThan(tokenSubject, reissueLimit);
        refreshToken.orElseThrow(()-> {throw new ExpiredJwtException(null, null, "리프레시 토큰이 만료되었습니다.");});
        refreshToken.ifPresent(refresh -> refreshToken.get().increaseReissueCount());
        System.out.println("refreshToken = " + refreshToken);
        System.out.println("refreshToken = " + refreshToken.get().getReissueCount());
    }
}