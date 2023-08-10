package com.example.todolist_backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String account;
    private String userName;
    private String email;
    private String password;
}
