package com.example.todolist_backend.dto.user;

import com.example.todolist_backend.domain.ToDo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserLoginData {
    private int id;
    // private Long id;
    private String account;
    private String userName;
}
