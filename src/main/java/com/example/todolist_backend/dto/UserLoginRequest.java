package com.example.todolist_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class UserLoginRequest {
    private String account;
    private String password;
}
