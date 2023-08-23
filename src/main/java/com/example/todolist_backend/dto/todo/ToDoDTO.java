package com.example.todolist_backend.dto.todo;

import com.example.todolist_backend.domain.ToDo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ToDoDTO {
    private Long id;
    private String content;
    private boolean done;

    public static ToDoDTO convertDTO(ToDo todo) {
        return new ToDoDTO(
                todo.getId(),
                todo.getContent(),
                todo.isDone()
        );
    }




}
