package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.user.UserLoginRequest;
import com.example.todolist_backend.dto.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.dto.user.UserLoginData;
import com.example.todolist_backend.dto.user.UserLoginResponse;
import com.example.todolist_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식의 데이터를 생성하고 응답 헤더를 설정하여 클라이언트에게 전달
@RequestMapping("/api/user") // spring 시큐리티 설정에서 토큰검증 로직에서 제외시킴.
@RequiredArgsConstructor // final 필드 또는 @NonNull 어노테이션이 붙은 필드를 대상으로 하는 생성자를 자동으로 생성
public class UserController {
    private final AuthService authService; // 생성자로 의존성주입

    // ResponseEntity : HTTP 응답을 표현하기 위한 클래스, 클라이언트에게 응답을 생성하고 반환(응답의 상태 코드, 헤더, 본문 등을 정의할 수 있다)
    // <String>: ResponseEntity 가 응답 본문으로 문자열(String) 데이터를 지정. 응답 본문은 클라이언트에게 전달될 데이터
    @PostMapping("/join")
    public ResponseDto<?> join(@RequestBody UserJoinRequest joinRequestBody) { // <?> : 타입 와일드카드
        ResponseDto<?> result = authService.join(joinRequestBody);
        return result; // 회원가입 응답 dto ==  result(회원가입 성공여부), message, data { null }
    }

    // 로그인 응답 dto ==  result(로그인 성공여부), message, data { accessToken, refreshToken, experTime(토큰만료시간), user(유저정보) }
    @PostMapping("/login")
    public ResponseDto<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequestBody) {
        ResponseDto<UserLoginResponse> result = authService.login(loginRequestBody);
        return result;
    }

    // 회원정보 조회 & 로그인 인증 - get 맵핑 - 토큰이용해서 로그인한 사용자만 요청할수있게끔
    @GetMapping("/detail")
    public ResponseDto<UserLoginData> detail(@RequestHeader(value = "Authorization") String token) {
        System.out.println("token = " + token);
        ResponseDto<UserLoginData> result = authService.getUserInfo(token);
        return result;
    }

//    @PostMapping("/reissue") // 토큰 재발급
//    public ResponseEntity<TokenDto> reissueToken(@RequestBody TokenRequestDto tokenRequestDto) { // 토큰 재발급을 위한 AccessToken / RefreshToken 존재
//        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
//    }

}
