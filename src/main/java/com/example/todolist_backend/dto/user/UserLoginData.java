package com.example.todolist_backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginData {
    private Long id;
    private String account;
    private String userName;
}
