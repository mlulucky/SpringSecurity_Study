package com.example.todolist_backend.dto.user;

import com.example.todolist_backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginResponse {
    private String token;
     private int experTime;
     private User user;
}
