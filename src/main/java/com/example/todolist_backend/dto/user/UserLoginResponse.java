package com.example.todolist_backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginResponse { // 유저 로그인 시 서버에서 응답하는 DTO
    private String token; // accessToken
    private int experTime;
    private UserLoginData user;
    private String refreshToken; // 리프레시토큰

}
