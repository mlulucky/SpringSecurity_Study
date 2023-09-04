package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.todo.ToDoDTO;
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

    @GetMapping("/{uId}/list")
    public ResponseEntity<List<ToDoDTO>> list(@PathVariable Long uId) { // 응답데이터 - List<ToDoDTO>
        List<ToDoDTO> todos = toDoService.list(uId);
        return ResponseEntity.ok().body(todos);
    }

    @PostMapping("/register")
    public ResponseEntity<ToDoCreateResponse> register(@RequestBody ToDoCreateRequest toDoCreateRequest) { // url 의 uId 를 받고, toDoCreateRequest 를 응답 body 에 받겠다.
        return ResponseEntity.ok().body(toDoService.createToDo(toDoCreateRequest));
    }

    @PatchMapping("/modify")
    public ResponseEntity<ToDoDTO> modify(@RequestBody ToDoDTO toDoDTO) {
        return ResponseEntity.ok().body(toDoService.modifyToDo(toDoDTO)); // 응답본문에 포함
    }

    @DeleteMapping("/{todoId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long todoId) {
        toDoService.deleteToDo(todoId);
        return ResponseEntity.ok().build(); // 상태코드만 반환
    }

    // security 인증 - 사용자 확인 테스트
    @GetMapping("/usercheck")
    public String getToDo(@AuthenticationPrincipal String account) { // jwtFilter 에 SecurityContext 에 인증할 객체로 account 를 담았기때문에, account 정보를 가져와 쓸수있다.
        return "로그인된 사용자는 " + account + "입니다.";
    }

}
