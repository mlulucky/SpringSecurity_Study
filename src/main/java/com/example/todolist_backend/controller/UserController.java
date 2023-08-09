package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.UserRequest;
import com.example.todolist_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequiredArgsConstructor // 자동으로 생성자 주입
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRequest dto) {
        return ResponseEntity.ok().body("회원가입 성공");
    }
}
