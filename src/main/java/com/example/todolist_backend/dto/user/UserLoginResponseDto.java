package com.example.todolist_backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginResponseDto { // 유저 로그인 시 서버에서 응답하는 DTO
    private String token;
    private int experTime;
}
