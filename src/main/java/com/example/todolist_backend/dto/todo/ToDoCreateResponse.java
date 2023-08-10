package com.example.todolist_backend.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ToDoCreateResponse {
    // private String userName;
    private String content;
    private boolean done;
    private String message; // 요청이 잘 처리됬는지 메세지
}
