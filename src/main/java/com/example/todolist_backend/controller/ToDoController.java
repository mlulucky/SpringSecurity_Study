package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
