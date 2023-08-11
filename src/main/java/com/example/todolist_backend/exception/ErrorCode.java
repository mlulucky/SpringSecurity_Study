package com.example.todolist_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),  // 유저네임 중복 에러 - 회원가입
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, ""); // 유저네임 없음 에러 - 로그인

    private HttpStatus httpStatus;
    private String message;
}
