package com.example.todolist_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class) // 해당 예외가 발생했을때 실행
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());

    }
}




