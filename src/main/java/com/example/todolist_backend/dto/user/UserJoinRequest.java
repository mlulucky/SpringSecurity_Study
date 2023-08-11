package com.example.todolist_backend.dto.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor // 🌈 JSON 직렬화 역직렬화 변환시 기본생성자(매개변수 없는) 필요
@Getter
public class UserJoinRequest {
    private String account;
    private String userName;
    private String email;
    private String password;
}
