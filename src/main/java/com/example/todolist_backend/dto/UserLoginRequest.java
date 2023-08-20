package com.example.todolist_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor // 기본생성자 // Type definition error: [simple type, class com.example.todolist_backend.dto.UserLoginRequest] 에러 해결
public class UserLoginRequest {
    @NotBlank
    private String account;
    @NotBlank
    private String password;
}
