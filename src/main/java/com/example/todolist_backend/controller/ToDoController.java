package com.example.todolist_backend.controller;

import com.example.todolist_backend.domain.ToDo;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @PostMapping("/{uId}/todos")
    public ResponseEntity<ToDoCreateResponse> register(@PathVariable Long uId, @RequestBody ToDoCreateRequest toDoCreateRequest) {
        // toDoService.createToDo(toDoCreateRequest);
        // return ResponseEntity.ok().body("투두 등록이 완료 되었습니다.");

        return ResponseEntity.ok().body(toDoService.createToDo(toDoCreateRequest));
    }

    @GetMapping("/{uId}/list")
    public ResponseEntity<List<ToDo>> list(@PathVariable Long uId) {
        return ResponseEntity.ok().body(toDoService.list(uId));

    }

    @GetMapping("/")
    public String getToDo(@AuthenticationPrincipal String account) { // jwtFilter 에 SecurityContext 에 인증할 객체로 account 를 담았기때문에, account 정보를 가져와 쓸수있다.
        return "로그인된 사용자는 " + account + "입니다.";
    }

}
