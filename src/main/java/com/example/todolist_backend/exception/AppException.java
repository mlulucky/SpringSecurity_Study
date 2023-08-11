package com.example.todolist_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private ErrorCode errorCode; // 커스텀 에러코드
    private String message; // 에러 메시지
}
