package com.example.todolist_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 유저네임 중복 에러 - 회원가입
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""), // HTTP 상태코드(confilct) 409, 에러메시지 ""
    // 유저네임 없음 에러 - 로그인
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    // 비밀번호 불일치 에러 - 로그인
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "");

    private HttpStatus httpStatus;
    private String message;
}
