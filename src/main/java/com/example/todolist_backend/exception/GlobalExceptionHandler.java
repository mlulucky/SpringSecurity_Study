package com.example.todolist_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice // 예외 발생 시 json 형태로 결과를 반환  // 전역 예외처리 클래스로 지정 // 전역적으로 컨트롤러 클래스에서 발생할 수 있는 예외를 처리 // @ControllerAdvice 와 @ResponseBody 가 합쳐진 어노테이션 // @ResponseBody 는 컨트롤러의 return 값으로 객체를 넘길 경우 Json 으로 변환해주는 어노테이션
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class) // 어떤 ExceptionClass 를 처리할지 설정
    public ResponseEntity<ErrorResponse> UserAlreadyExistException(){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("동일한 계정이 존재합니다."));
    }



//    // RuntimeException 예외처리
//    @ExceptionHandler(RuntimeException.class) // @ExceptionHandler( ) : 해당 예외가 발생했을때 실행
//    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) { // ResponseEntity<?> : 응답의 본문 데이터 타입을 ?(와일드카드) 다양한 유형의 응답 데이터를 처리 // HTTP 응답을 나타내는 클래스 // HTTP 응답을 생성하고 반환
//        return ResponseEntity.status(HttpStatus.CONFLICT)
//                .body(e.getMessage());
//    }
//
//    // AppException 예외처리
//    @ExceptionHandler(AppException.class)
//    public ResponseEntity<?> appExceptionHandler(AppException e) {
//        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
//                .body(e.getErrorCode().name() + " " + e.getMessage());
//    }



}




