package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.dto.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.dto.user.UserLoginResponse;
import com.example.todolist_backend.service.AuthService;
import com.example.todolist_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식의 데이터를 생성하고 응답 헤더를 설정하여 클라이언트에게 전달
@RequestMapping("/api/user")
@RequiredArgsConstructor // final 필드 또는 @NonNull 어노테이션이 붙은 필드를 대상으로 하는 생성자를 자동으로 생성
public class UserController {
    private final AuthService authService; // 생성자로 의존성주입

    // ResponseEntity : HTTP 응답을 표현하기 위한 클래스, 클라이언트에게 응답을 생성하고 반환(응답의 상태 코드, 헤더, 본문 등을 정의할 수 있다)
    // <String>: ResponseEntity 가 응답 본문으로 문자열(String) 데이터를 지정. 응답 본문은 클라이언트에게 전달될 데이터
    @PostMapping("/join")
    public ResponseDto<?> join(@RequestBody UserJoinRequest joinRequestBody)  { // <?> : 타입 와일드카드
        ResponseDto<?> result = authService.join(joinRequestBody);
        return result; // 회원가입 응답 dto ==  result(회원가입 성공여부), message, data { null }
    }

    // 로그인 응답 dto ==  result(로그인 성공여부), message, data { accessToken, experTime(토큰만료시간), user(유저정보) }
    @PostMapping("/login")
    public ResponseDto<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequestBody, HttpServletResponse response) {
        ResponseDto<UserLoginResponse> result = authService.login(loginRequestBody, response);
        return result;
    }

}
