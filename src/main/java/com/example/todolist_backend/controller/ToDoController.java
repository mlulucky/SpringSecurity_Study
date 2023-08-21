package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @PostMapping("{id}")
    public ResponseEntity<ToDoCreateResponse> writeToDo(@RequestBody ToDoCreateRequest toDoCreateRequest) {
        // toDoService.createToDo(toDoCreateRequest);
        // return ResponseEntity.ok().body("투두 등록이 완료 되었습니다.");
        return ResponseEntity.ok().body(toDoService.createToDo(toDoCreateRequest));
    }

    @GetMapping("/")
    public String getToDo(@AuthenticationPrincipal String account) { // jwtFilter 에 SecurityContext 에 인증할 객체로 account 를 담았기때문에, account 정보를 가져와 쓸수있다.
        return "로그인된 사용자는 " + account + "입니다.";
    }

}
