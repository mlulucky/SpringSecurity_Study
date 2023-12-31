package com.example.todolist_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 전역 예외처리 클래스로 지정 // 전역적으로 컨트롤러 클래스에서 발생할 수 있는 예외를 처리하고 관리하는 데 사용 // UserController 에서 회원가입 userService.join 호출시 userService 에서 에러처리 RuntimeException 발생시 에러처리됨.
public class ExceptionManager {

    // RuntimeException 예외처리
    @ExceptionHandler(RuntimeException.class) // @ExceptionHandler( ) : 해당 예외가 발생했을때 실행
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) { // ResponseEntity<?> : 응답의 본문 데이터 타입을 ?(와일드카드) 다양한 유형의 응답 데이터를 처리 // HTTP 응답을 나타내는 클래스 // HTTP 응답을 생성하고 반환
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    // AppException 예외처리
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name() + " " + e.getMessage());
    }
}




